/**
 * This software is dual-licensed. You may choose either the
 * Non-Profit Open Software License version 3.0, or any license
 * agreement into which you enter with Security Innovation, Inc.
 * 
 * Use of this code, or certain portions thereof, implements
 * inventions covered by claims of one or more of the following
 * U.S. Patents and/or foreign counterpart patents, owned by
 * Security Innovation, Inc.:
 * 7,308,097, 7,031,468, 6,959,085, 6,298,137, and 6,081,597.
 * Practice or sale of the inventions embodied in the code hereof
 * requires a license from Security Innovation Inc. at:
 * 
 * 187 Ballardvale St, Suite A195
 * Wilmington, MA 01887
 * USA
 */

package net.sf.ntru.sign;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import net.sf.ntru.sign.NtruSign;
import net.sf.ntru.sign.SignatureKeyPair;
import net.sf.ntru.sign.SignatureParameters;
import net.sf.ntru.sign.SignaturePrivateKey;
import net.sf.ntru.sign.SignaturePublicKey;

import org.junit.Test;
import static org.junit.Assert.assertArrayEquals;

public class SignatureKeyTest {
    
    @Test
    public void testEncode() throws IOException {
        SignatureParameters params = SignatureParameters.TEST157;
        NtruSign ntru = new NtruSign(params);
        SignatureKeyPair kp = ntru.generateKeyPair();
        byte[] priv = kp.priv.getEncoded();
        byte[] pub = kp.pub.getEncoded();
        SignatureKeyPair kp2 = new SignatureKeyPair(new SignaturePrivateKey(priv, params), new SignaturePublicKey(pub, params));
        byte[] priv2 = kp2.priv.getEncoded();
        assertArrayEquals(priv, priv2);
        byte[] pub2 = kp2.pub.getEncoded();
        assertArrayEquals(pub, pub2);
        
        ByteArrayOutputStream bos1 = new ByteArrayOutputStream();
        ByteArrayOutputStream bos2 = new ByteArrayOutputStream();
        kp.priv.writeTo(bos1);
        kp.pub.writeTo(bos2);
        ByteArrayInputStream bis1 = new ByteArrayInputStream(bos1.toByteArray());
        ByteArrayInputStream bis2 = new ByteArrayInputStream(bos2.toByteArray());
        SignatureKeyPair kp3 = new SignatureKeyPair(new SignaturePrivateKey(bis1, params), new SignaturePublicKey(bis2, params));
        byte[] priv3 = kp3.priv.getEncoded();
        assertArrayEquals(priv, priv3);
        byte[] pub3 = kp3.pub.getEncoded();
        assertArrayEquals(pub, pub3);
    }
}