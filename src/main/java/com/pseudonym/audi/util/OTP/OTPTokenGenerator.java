package com.pseudonym.audi.util.OTP;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageConfig;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.pseudonym.audi.exception.CustomException;
import com.pseudonym.audi.exception.ErrorCode;
import org.apache.commons.codec.binary.Base32;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.SecureRandom;

public class OTPTokenGenerator {

    private static String GOOGLE_URL = "https://www.google.com/chart?chs=200x200&chld=M|0&cht=qr&chl=";
    private static String ISSUER = "PSEUDONYM_AUDI";

    public static String generateSecretKey() {
        SecureRandom secureRandom = new SecureRandom();
        byte[] bytes = new byte[20];
        secureRandom.nextBytes(bytes);
        Base32 base32 = new Base32();

        return base32.encodeToString(bytes);
    }

    public static String getGoogleOTPAuthURL(String secretKey, String id) {
        try {
            return GOOGLE_URL+"otpauth://totp/"
                    + URLEncoder.encode(ISSUER + ":" + id, "UTF-8").replace("+", "%20")
                    + "?secret=" + URLEncoder.encode(secretKey, "UTF-8").replace("+", "20")
                    + "&issuer=" + URLEncoder.encode(ISSUER, "UTF-8").replace("+","%20");
        } catch ( UnsupportedEncodingException e) {
            throw new CustomException(ErrorCode.INVALID_ENCODING_OTP_URL);
        }
    }

    // 이메일에 이미지 넣어서 전송하는 방법 향후 추가 필요
    public static byte[] getQRCodeImage(String googleOTPAuthURL,
                             int height, int width) throws WriterException, IOException {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(googleOTPAuthURL, BarcodeFormat.QR_CODE, width, height);

        ByteArrayOutputStream pngOutputStream = new ByteArrayOutputStream();
        MatrixToImageConfig con = new MatrixToImageConfig( 0xFF000002 , 0xFFFFC041 ) ;

        MatrixToImageWriter.writeToStream(bitMatrix, "PNG", pngOutputStream,con);
        byte[] pngData = pngOutputStream.toByteArray();
        return pngData;
    }
}
