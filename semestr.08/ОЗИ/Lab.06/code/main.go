package main

import (
	"crypto/rsa"
	"crypto/rand"
	"fmt"
	"crypto/x509"
	"encoding/pem"
	"errors"
	"bufio"
	"os"
	"strings"
	"io/ioutil"
	"encoding/base64"
	"crypto/sha256"
	"crypto"
)

func main() {
	reader := bufio.NewReader(os.Stdin)

	generatePair := true
	if _, err1 := os.Stat("private.pem"); os.IsNotExist(err1) {
		fmt.Println("Приватный ключ не существует")
	} else if _, err1 := os.Stat("public.pem"); os.IsNotExist(err1) {
		fmt.Println("Публичнй ключ не существует")
	} else {
		fmt.Print("Создать новые ключи? [да] или [нет]: ")
		createKeys, _ := reader.ReadString('\n')
		for strings.TrimSpace(createKeys) != "да" && strings.TrimSpace(createKeys) != "нет" {
			fmt.Print("Возможные варианты [да] или [нет]: ")
			tmp, _ := reader.ReadString('\n')
			createKeys = tmp
		}
		if strings.TrimSpace(createKeys) == "нет" {
			generatePair = false
		}
	}

	var privateKey *rsa.PrivateKey
	var publicKey *rsa.PublicKey

	if (generatePair) {
		fmt.Println("Создаю и сохраняю новую пару ключей")
		privateKey, publicKey = GenerateRsaKeyPair()
		SaveFile("private.pem", ExportRsaPrivateKeyAsPemStr(privateKey))
		SaveFile("public.pem", ExportRsaPublicKeyAsPemStr(publicKey))
	} else {
		privateKey, _ = ParseRsaPrivateKeyFromPemStr(ReadFile("private.pem"))
		publicKey, _ = ParseRsaPublicKeyFromPemStr(ReadFile("public.pem"))
	}

	fmt.Print("Шифрование[1], расшифрование[2], подпись[3] или верификация[4]: ")
	mode, _ := reader.ReadString('\n')
	for strings.TrimSpace(mode) != "1" && strings.TrimSpace(mode) != "2" && strings.TrimSpace(mode) != "3" && strings.TrimSpace(mode) != "4" {
		fmt.Print("Возможные вариант [1], [2], [3] или [4]: ")
		tmp, _ := reader.ReadString('\n')
		mode = tmp
	}
	mode = strings.TrimSpace(mode)

	switch mode {
	case "1":
		fmt.Print("Введите открытый текст: ")
		text, _ := reader.ReadString('\n')
		text = strings.TrimSpace(text)
		bytes, _ := rsa.EncryptPKCS1v15(rand.Reader, publicKey, []byte(text))
		fmt.Println("Шифрованый текст:")
		fmt.Println(base64.StdEncoding.EncodeToString(bytes))
	case "2":
		fmt.Print("Введите шифрованый текст: ")
		text, _ := reader.ReadString('\n')
		closedBytes, _ := base64.StdEncoding.DecodeString(strings.TrimSpace(text))
		bytes, _ := rsa.DecryptPKCS1v15(rand.Reader, privateKey, closedBytes)
		fmt.Println("Открытый текст текст:")
		fmt.Println(string(bytes))
	case "3":
		fmt.Print("Введите открытый текст: ")
		text, _ := reader.ReadString('\n')
		text = strings.TrimSpace(text)
		hashed := sha256.Sum256([]byte(text))
		bytes, _ := rsa.SignPKCS1v15(rand.Reader, privateKey, crypto.SHA256, hashed[:])
		fmt.Println("Подпись:")
		fmt.Println(base64.StdEncoding.EncodeToString(bytes))
	case "4":
		fmt.Print("Введите открытый текст: ")
		text, _ := reader.ReadString('\n')
		text = strings.TrimSpace(text)
		fmt.Print("Введите подпись: ")
		signature, _ := reader.ReadString('\n')
		hashed := sha256.Sum256([]byte(text))
		signatureBytes, _ := base64.StdEncoding.DecodeString(strings.TrimSpace(signature))
		err := rsa.VerifyPKCS1v15(publicKey, crypto.SHA256, hashed[:], signatureBytes)
		if err == nil {
			fmt.Println("Подпись верна")
		} else {
			fmt.Println("Подпись не верна")
		}
	}

}

func SaveFile(fileName, content string) int {
	file, _ := os.Create(fileName)
	defer file.Close()

	n, _ := file.WriteString(content)

	return n
}

func ReadFile(fileName string) string {
	bytes, _ := ioutil.ReadFile(fileName)
	return string(bytes)
}

func GenerateRsaKeyPair() (*rsa.PrivateKey, *rsa.PublicKey) {
	privkey, _ := rsa.GenerateKey(rand.Reader, 1024)
	return privkey, &privkey.PublicKey
}

func ExportRsaPrivateKeyAsPemStr(privkey *rsa.PrivateKey) string {
	privkeyBytes := x509.MarshalPKCS1PrivateKey(privkey)
	privkeyPem := pem.EncodeToMemory(
		&pem.Block{
			Type:  "RSA PRIVATE KEY",
			Bytes: privkeyBytes,
		},
	)
	return string(privkeyPem)
}

func ParseRsaPrivateKeyFromPemStr(privPEM string) (*rsa.PrivateKey, error) {
	block, _ := pem.Decode([]byte(privPEM))
	if block == nil {
		return nil, errors.New("failed to parse PEM block containing the key")
	}

	priv, err := x509.ParsePKCS1PrivateKey(block.Bytes)
	if err != nil {
		return nil, err
	}

	return priv, nil
}

func ExportRsaPublicKeyAsPemStr(pubkey *rsa.PublicKey) string {
	pubkeyBytes, err := x509.MarshalPKIXPublicKey(pubkey)
	if err != nil {
		return ""
	}
	pubkeyPem := pem.EncodeToMemory(
		&pem.Block{
			Type:  "RSA PUBLIC KEY",
			Bytes: pubkeyBytes,
		},
	)

	return string(pubkeyPem)
}

func ParseRsaPublicKeyFromPemStr(pubPEM string) (*rsa.PublicKey, error) {
	block, _ := pem.Decode([]byte(pubPEM))
	if block == nil {
		return nil, errors.New("failed to parse PEM block containing the key")
	}

	pub, err := x509.ParsePKIXPublicKey(block.Bytes)
	if err != nil {
		return nil, err
	}

	switch pub := pub.(type) {
	case *rsa.PublicKey:
		return pub, nil
	default:
		break
	}
	return nil, errors.New("Key type is not RSA")
}
