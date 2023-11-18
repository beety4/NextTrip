package config;

import java.security.MessageDigest;
import java.util.Random;

public class CryptoModule {
	// SHA256 암호화
	public String getSHA256(String input) {
		String saltValue = "NextTrip WELCOME!";
		StringBuffer result = new StringBuffer();
		try {
        	// 입력한 값에 Hash Algo 적용
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			/****** 가염 처리과정 ******/
			byte[] salt = saltValue.getBytes();
			digest.reset();
			digest.update(salt);
			byte[] chars = digest.digest(input.getBytes("UTF-8")); // Hash 적용 값을 담아준다.
			/******* chars -> 16진수 String으로 변환 *******/
			for(int i = 0; i < chars.length; i++) {
				String hex = Integer.toHexString(0xff & chars[i]);
				if(hex.length() == 1) result.append("0"); // 항상 두 자리 16진수로
				result.append(hex);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return result.toString();
	}
	

	// 인증 코드용 5자리 문자/숫자
	public String getNewKey(int keyLength) {
        StringBuffer key = new StringBuffer();
        Random rnd = new Random();
 
        for (int i = 0; i < keyLength; i++) {
            int index = rnd.nextInt(3);
            switch (index) {
                case 0:	// 소문자
                    key.append((char) ((int) (rnd.nextInt(26)) + 97));
                    break;
                case 1:	// 대문자
                    key.append((char) ((int) (rnd.nextInt(26)) + 65));
                    break;
                case 2:	// 숫자
                    key.append((rnd.nextInt(10)));
                    break;
            }
        }
        return key.toString();
    }

}
