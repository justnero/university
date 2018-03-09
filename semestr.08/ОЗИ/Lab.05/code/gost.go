package main

import (
    "encoding/base64"
    "fmt"
    "math/rand"
    "bytes"
    "math"
    "encoding/binary"
    "bufio"
    "strings"
    "os"
)

type Gost struct {
    table [8][16]byte
}

type Gost_Mode uint

const (
    GOST_REPLACEMENT = Gost_Mode(1)
    GOST_XORING      = Gost_Mode(2)
)

func (g Gost) encryptStr(data, key string, mode Gost_Mode) string {
    return base64.StdEncoding.EncodeToString(g.encrypt([]byte(data), []byte(key), mode))
}

func (g Gost) decryptStr(data, key string, mode Gost_Mode) string {
    base64decoded, _ := base64.StdEncoding.DecodeString(data)
    return string(g.decrypt([]byte(base64decoded), []byte(key), mode))
}

func (g Gost) encrypt(data, key []byte, mode Gost_Mode) []byte {
    closedData := new(bytes.Buffer)
    switch mode {
    case GOST_REPLACEMENT:
        for len(data)%8 > 0 {
            data = append(data, 0)
        }
        for i := 0; i < len(data); i+=8 {
            blockBytes := data[i : i+8]
            block := g.encryptionCycle(g.bytesToUint64(blockBytes), key)
            closedData.Write(g.uint64ToBytes(block))
        }
    default:
        fallthrough
    case GOST_XORING:
        rand.Seed(179426549)
        openBlock := uint64(0)
        for i := 0; i < len(data); i++ {
            openBlock |= uint64(data[i]) << uint(8*(i%8))
            if ((i+1)%8 == 0 && i != 0) || i == len(data)-1 {
                buffer := uint64(rand.Int31())
                gamma := g.encryptionCycle(buffer, key)
                closedData.Write(g.uint64ToBytes(openBlock^gamma))
                openBlock = 0
            }
        }
    }
    return closedData.Bytes()
}

func (g Gost) decrypt(data, key []byte, mode Gost_Mode) []byte {
    switch mode {
    case GOST_REPLACEMENT:
        openData := new(bytes.Buffer)
        for i := 0; i < len(data); i+=8 {
            blockBytes := data[i : i+8]
            block := g.decryptionCycle(g.bytesToUint64(blockBytes), key)
            openData.Write(g.uint64ToBytes(block))
        }
        return openData.Bytes()
    default:
        fallthrough
    case GOST_XORING:
        return g.encrypt(data, key, mode)
    }
}

func (g Gost) uint64ToBytes(block uint64) []byte {
    bytes := make([]byte, 8, 8)
    binary.LittleEndian.PutUint64(bytes, block)
    return bytes
}

func (g Gost) bytesToUint64(bytes []byte) uint64 {
    return binary.LittleEndian.Uint64(bytes)
}

func (g Gost) swap(block uint64) uint64 {
    return ((block & 0xFFFFFFFF00000000) >> 32) | ((block & 0x00000000FFFFFFFF) << 32)
}

func (g Gost) unionBlock(part1, part2 uint64) uint64 {
    return part1 << 32 | part2
}

func (g Gost) tableReplacement(block uint64) uint64 {
    r := uint64(0)
    for i := 0; i < 8; i++ {
        index:= (block>>uint(4 * i))&0x0F
        r |= uint64(g.table[i][index]) << uint(4 * i)
    }
    return r
}

func (g Gost) step(block uint64, key byte) uint64 {
    part1 := block & 0x00000000FFFFFFFF
    part2 := block >> 32

    s := (part1 + uint64(key)) % math.MaxUint32
    s = g.tableReplacement(s)
    s = s<<11 | s>>21
    s = s ^ part2
    return g.unionBlock(s, part1)
}

func (g Gost) encryptionCycle(block uint64, key []byte) uint64 {
    r:= block
    for k := 0; k < 3; k++ {
        for j := 0; j < len(key); j++ {
            r = g.step(r, key[j])
        }
    }
    for j := len(key) - 1; j >= 0; j-- {
        r = g.step(r, key[j])
    }
    return g.swap(r)
}

func (g Gost) decryptionCycle(block uint64, key []byte) uint64 {
    r:= block
    for j := 0; j < len(key); j++ {
        r = g.step(r, key[j])
    }
    for k := 0; k < 3; k++ {
        for j := len(key) - 1; j >= 0; j-- {
            r = g.step(r, key[j])
        }
    }
    return g.swap(r)
}

func readKey(reader *bufio.Reader, size int) string {
    fmt.Printf("Введите ключ длиной %d символов: ", size)
    value, _ := reader.ReadString('\n')
    for len([]byte(strings.TrimSpace(value))) != size {
        fmt.Printf("Убедитесь, что длина ключа %d символов: ", size)
        tmp, _ := reader.ReadString('\n')
        value = tmp
    }
    return value
}

func main() {
    gost:= Gost{table: [8][16]byte{
        {4, 10, 9, 2, 13, 8, 0, 14, 6, 11, 1, 12, 7, 15, 5, 3},
        {14, 11, 4, 12, 6, 13, 15, 10, 2, 3, 8, 1, 0, 7, 5, 9},
        {5, 8, 1, 13, 10, 3, 4, 2, 14, 15, 12, 7, 6, 0, 9, 11},
        {7, 13, 10, 1, 0, 8, 9, 15, 14, 4, 6, 12, 11, 2, 5, 3},
        {6, 12, 7, 1, 5, 15, 13, 8, 4, 10, 9, 14, 0, 3, 11, 2},
        {4, 11, 10, 0, 7, 2, 1, 13, 3, 6, 8, 5, 9, 12, 15, 14},
        {13, 11, 4, 1, 3, 15, 5, 9, 0, 10, 14, 7, 6, 8, 2, 12},
        {1, 15, 13, 0, 5, 7, 10, 4, 9, 2, 3, 14, 6, 11, 8, 12}}}

    reader := bufio.NewReader(os.Stdin)
    fmt.Print("Введите текст: ")
    text, _ := reader.ReadString('\n')
    text = strings.TrimSpace(text)

    key := readKey(reader, 32)

    fmt.Print("Простая замена[1] или гаммирование[2]: ")
    mode, _ := reader.ReadString('\n')
    for strings.TrimSpace(mode) != "1" && strings.TrimSpace(mode) != "2" {
        fmt.Print("Возможные вариант [1] или [2]: ")
        tmp, _ := reader.ReadString('\n')
        mode = tmp
    }

    fmt.Print("Зашифровать[1] или расшифровать[2]: ")
    direction, _ := reader.ReadString('\n')
    for strings.TrimSpace(direction) != "1" && strings.TrimSpace(direction) != "2" {
        fmt.Print("Возможные вариант [1] или [2]: ")
        tmp, _ := reader.ReadString('\n')
        direction = tmp
    }

    modeId := GOST_XORING
    switch strings.TrimSpace(mode) {
    case "1":
        modeId = GOST_REPLACEMENT
    case "2":
        modeId = GOST_XORING
    }

    if strings.TrimSpace(direction) == "1" {
        fmt.Printf("Зашифрованный текст: %s\n", gost.encryptStr(text, key, modeId))
    } else {
        fmt.Printf("Расшифрованный текст: %s\n", gost.decryptStr(text, key, modeId))
    }
}