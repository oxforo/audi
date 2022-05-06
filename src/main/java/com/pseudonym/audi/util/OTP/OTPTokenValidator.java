package com.pseudonym.audi.util.OTP;

import de.taimos.totp.TOTP;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.codec.binary.Base32;
import org.apache.commons.codec.binary.Hex;

@NoArgsConstructor
@Setter
public class OTPTokenValidator {

    private String secretKey;

    public boolean validate(String inputCode) {
        String code = getOTPCode();
        return code.equals(inputCode);
    }

    private String getOTPCode() {
        Base32 base32 = new Base32();
        byte[] bytes = base32.decode(secretKey);
        String hexKey = Hex.encodeHexString(bytes);
        return TOTP.getOTP(hexKey);
    }
}
