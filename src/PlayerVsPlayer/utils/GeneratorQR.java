package PlayerVsPlayer.utils;

import java.io.IOException;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import arc.graphics.Pixmap;

public class GeneratorQR {
    private static Integer sizeQR = 250;

    /*
     * createQRTexture creates a QR code Pixmap based on a string and the fixed size
     * sizeQR
     */
    public Pixmap createQRTexture(String text)
            throws WriterException, IOException {

        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, sizeQR, sizeQR);

        // Create pixmap
        Pixmap pixmap = new Pixmap(sizeQR, sizeQR);

        // Fetch QR bitMatrix and change bits with relative color
        for (int y = 0; y < sizeQR; y++) {
            for (int x = 0; x < sizeQR; x++) {
                int color = bitMatrix.get(x, y) ? 0xFF000000 : 0xFFFFFFFF; // White or Black
                pixmap.set(x, y, color);
            }
        }

        return pixmap;
    }

    /*
     * createQRTexture creates a QR code Pixmap based on a string and a size
     */
    public Pixmap createQRTexture(String text, int size)
            throws WriterException, IOException {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, size, size);

        // Create pixmap
        Pixmap pixmap = new Pixmap(size, size);

        // Fetch QR bitMatrix and change bits with relative color
        for (int y = 0; y < size; y++) {
            for (int x = 0; x < size; x++) {
                int color = bitMatrix.get(x, y) ? 0xFF000000 : 0xFFFFFFFF; // White or Black
                pixmap.set(x, y, color);
            }
        }

        return pixmap;
    }
}
