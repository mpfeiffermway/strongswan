/*
 * Copyright (C) 2015 Martin Willi
 *
 * Copyright (C) secunet Security Networks AG
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by the
 * Free Software Foundation; either version 2 of the Licenseor (at your
 * option) any later version. See <http://www.fsf.org/copyleft/gpl.txt>.
 *
 * This program is distributed in the hope that it will be usefulbut
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
 * or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License
 * for more details.
 */

#include <crypto/crypto_tester.h>

/**
 * From RFC 7748
 */
dh_test_vector_t curve25519_1 = {
	.group = CURVE_25519, .priv_len = 32, .pub_len = 32, .shared_len = 32,
	.priv_a	= "\x77\x07\x6d\x0a\x73\x18\xa5\x7d\x3c\x16\xc1\x72\x51\xb2\x66\x45"
			  "\xdf\x4c\x2f\x87\xeb\xc0\x99\x2a\xb1\x77\xfb\xa5\x1d\xb9\x2c\x2a",
	.priv_b	= "\x5d\xab\x08\x7e\x62\x4a\x8a\x4b\x79\xe1\x7f\x8b\x83\x80\x0e\xe6"
			  "\x6f\x3b\xb1\x29\x26\x18\xb6\xfd\x1c\x2f\x8b\x27\xff\x88\xe0\xeb",
	.pub_a	= "\x85\x20\xf0\x09\x89\x30\xa7\x54\x74\x8b\x7d\xdc\xb4\x3e\xf7\x5a"
			  "\x0d\xbf\x3a\x0d\x26\x38\x1a\xf4\xeb\xa4\xa9\x8e\xaa\x9b\x4e\x6a",
	.pub_b	= "\xde\x9e\xdb\x7d\x7b\x7d\xc1\xb4\xd3\x5b\x61\xc2\xec\xe4\x35\x37"
			  "\x3f\x83\x43\xc8\x5b\x78\x67\x4d\xad\xfc\x7e\x14\x6f\x88\x2b\x4f",
	.shared	= "\x4a\x5d\x9d\x5b\xa4\xce\x2d\xe1\x72\x8e\x3b\xf4\x80\x35\x0f\x25"
			  "\xe0\x7e\x21\xc9\x47\xd1\x9e\x33\x76\xf0\x9b\x3c\x1e\x16\x17\x42",
};

/**
 * From RFC 8031
 */
dh_test_vector_t curve25519_2 = {
	.group = CURVE_25519, .priv_len = 32, .pub_len = 32, .shared_len = 32,
	.priv_a	= "\x75\x1f\xb4\x30\x86\x55\xb4\x76\xb6\x78\x9b\x73\x25\xf9\xea\x8c"
			  "\xdd\xd1\x6a\x58\x53\x3f\xf6\xd9\xe6\x00\x09\x46\x4a\x5f\x9d\x94",
	.priv_b	= "\x0a\x54\x64\x52\x53\x29\x0d\x60\xdd\xad\xd0\xe0\x30\xba\xcd\x9e"
			  "\x55\x01\xef\xdc\x22\x07\x55\xa1\xe9\x78\xf1\xb8\x39\xa0\x56\x88",
	.pub_a	= "\x48\xd5\xdd\xd4\x06\x12\x57\xba\x16\x6f\xa3\xf9\xbb\xdb\x74\xf1"
			  "\xa4\xe8\x1c\x08\x93\x84\xfa\x77\xf7\x90\x70\x9f\x0d\xfb\xc7\x66",
	.pub_b	= "\x0b\xe7\xc1\xf5\xaa\xd8\x7d\x7e\x44\x86\x62\x67\x32\x98\xa4\x43"
			  "\x47\x8b\x85\x97\x45\x17\x9e\xaf\x56\x4c\x79\xc0\xef\x6e\xee\x25",
	.shared	= "\xc7\x49\x50\x60\x7a\x12\x32\x7f\x32\x04\xd9\x4b\x68\x25\xbf\xb0"
			  "\x68\xb7\xf8\x31\x9a\x9e\x37\x08\xed\x3d\x43\xce\x81\x30\xc9\x50",
};
