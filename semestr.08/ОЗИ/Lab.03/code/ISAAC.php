<?php

class ISAAC {
    private $m, $a, $b, $c;
    public  $r;

    public function isaac() {
        $c = ++$this->c;
        $b = $this->b += $c;
        $a = $this->a;

        $m =& $this->m;
        $r = array();

        for ($i = 0; $i < 256; ++$i) {
            $x = $m[$i];
            switch ($i & 3) {
            case 0: $a ^= ($a << 13); break;
            case 1: $a ^= ($a >>  6) & 0x03ffffff; break;
            case 2: $a ^= ($a <<  2); break;
            case 3: $a ^= ($a >> 16) & 0x0000ffff; break;
            }
            $a += $m[$i ^ 128]; $a &= 0xffffffff;
            $m[$i] = $y = ($m[($x >>  2) & 255] + $a + $b) & 0xffffffff;
            $r[$i] = $b = ($m[($y >> 10) & 255] + $x) & 0xffffffff;
        }

        $this->a = $a;
        $this->b = $b;
        $this->c = $c;
        $this->r = $r;
    }

    public function rand($len = 1) {
    	$result = '';
    	for($i=0;$i<$len;$i+=2) {
    		if (empty($this->r)) {
    			$this->isaac();
    		}
    		$result .= dechex(array_pop($this->r));
    	}
        return $result;
    }

    private static function mix( &$a, &$b, &$c, &$d, &$e, &$f, &$g, &$h ) {
        $a ^= ($b << 11);              $d += $a; $b += $c;
        $b ^= ($c >>  2) & 0x3fffffff; $e += $b; $c += $d;
        $c ^= ($d <<  8);              $f += $c; $d += $e;
        $d ^= ($e >> 16) & 0x0000ffff; $g += $d; $e += $f;
        $e ^= ($f << 10);              $h += $e; $f += $g;
        $f ^= ($g >>  4) & 0x0fffffff; $a += $f; $g += $h;
        $g ^= ($h <<  8);              $b += $g; $h += $a;
        $h ^= ($a >>  9) & 0x007fffff; $c += $h; $a += $b;

        $a &= 0xffffffff; $b &= 0xffffffff; $c &= 0xffffffff; $d &= 0xffffffff;
        $e &= 0xffffffff; $f &= 0xffffffff; $g &= 0xffffffff; $h &= 0xffffffff;
    }

    public function __construct ( $seed = null ) {
        $this->a = $this->b = $this->c = 0;
        $this->m = array_fill(0, 256, 0);
        $m =& $this->m;

        $a = $b = $c = $d = $e = $f = $g = $h = 0x9e3779b9;

        for ($i = 0; $i < 4; ++$i) {
            ISAAC::mix($a, $b, $c, $d, $e, $f, $g, $h);
        }

        if ( isset($seed) ) {
            if ( is_string($seed) ) {
                $seed = array_values(unpack("V256", pack("a1024", $seed)));
            }

            for ($i = 0; $i < 256; $i += 8) {
                $a += $seed[$i  ]; $b += $seed[$i+1];
                $c += $seed[$i+2]; $d += $seed[$i+3];
                $e += $seed[$i+4]; $f += $seed[$i+5];
                $g += $seed[$i+6]; $h += $seed[$i+7];
                ISAAC::mix($a, $b, $c, $d, $e, $f, $g, $h);
                $m[$i  ] = $a; $m[$i+1] = $b; $m[$i+2] = $c; $m[$i+3] = $d;
                $m[$i+4] = $e; $m[$i+5] = $f; $m[$i+6] = $g; $m[$i+7] = $h;
            }

            for ($i = 0; $i < 256; $i += 8) {
                $a += $m[$i  ]; $b += $m[$i+1]; $c += $m[$i+2]; $d += $m[$i+3];
                $e += $m[$i+4]; $f += $m[$i+5]; $g += $m[$i+6]; $h += $m[$i+7];
                ISAAC::mix($a, $b, $c, $d, $e, $f, $g, $h);
                $m[$i  ] = $a; $m[$i+1] = $b; $m[$i+2] = $c; $m[$i+3] = $d;
                $m[$i+4] = $e; $m[$i+5] = $f; $m[$i+6] = $g; $m[$i+7] = $h;
            }
        }
        else {
            for ($i = 0; $i < 256; $i += 8) {
                ISAAC::mix($a, $b, $c, $d, $e, $f, $g, $h);
                $m[$i  ] = $a; $m[$i+1] = $b; $m[$i+2] = $c; $m[$i+3] = $d;
                $m[$i+4] = $e; $m[$i+5] = $f; $m[$i+6] = $g; $m[$i+7] = $h;
            }
        }

        // fill in the first set of results
        $this->isaac();
    }
}

function isaac($key, $len) {
	return (new ISAAC($key))->rand($len);
}