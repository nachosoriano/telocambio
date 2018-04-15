/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pfc.soriano.wsdbmodel;

import com.pfc.soriano.wsdbmodel.exception.DbModelException;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.imageio.ImageIO;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.xml.bind.DatatypeConverter;

/**
 *
 * @author NACHO
 */
public class Utils {

    public enum EEntityType {

        USUARIO("src/main/webapp/img/usuario/"),
        TRUEQUE("src/main/webapp/img/trueque/");

        private final String imgPath;

        private EEntityType(String imgPath) {
            this.imgPath = imgPath;
        }

        public String getImgPath() {
            return imgPath;
        }
    }

    public static Collection<String> getImages(String prefix, Utils.EEntityType tipo) {
        List<String> result = new ArrayList<>();
        File directorio = new File(tipo.getImgPath());
        FiltroImagenes filtro = new FiltroImagenes(prefix);
        for (File file : directorio.listFiles(filtro)) {
            result.add(file.getName());
        }
        return result;
    }

    public static void createImage(Long id, String fotoNombre, byte[] foto, EEntityType entityType, boolean delete) throws DbModelException {
        FileOutputStream fos = null;
        Collection<String> lFiles = getImages(id.toString(), entityType);
        if (delete) {
            for (String fileName : lFiles) {
                File file = new File(entityType.getImgPath() + fileName);
                file.delete();
                File thumb = new File(entityType.getImgPath() + "thumb/" + fileName);
                if (thumb.exists()) {
                    thumb.delete();
                }
            }
            lFiles = new ArrayList<>();
        }
        String extension = "jpg";
        if (fotoNombre.contains(".")) {
            extension = fotoNombre.substring(fotoNombre.lastIndexOf(".") + 1);
        }
        String fileName = id + "_" + lFiles.size() + "_" + System.currentTimeMillis() + "." + extension;
        File img = new File(entityType.getImgPath() + fileName);
        try {
            if (!img.exists()) {
                img.createNewFile();
            }
            String str = new String(foto);
            fos = new FileOutputStream(img);
            fos.write(DatatypeConverter.parseBase64Binary(str.substring(str.indexOf(",") + 1)));
        } catch (IOException ex) {
            throw new DbModelException(ex.getLocalizedMessage(), ex);
        } finally {
            try {
                if (fos != null) {
                    fos.close();
                    //Thumbnail
                    BufferedImage bi = ImageIO.read(img);
                    BufferedImage thumb = new BufferedImage(bi.getWidth() / 2, bi.getHeight() / 2, BufferedImage.TYPE_INT_RGB);
                    thumb.createGraphics().drawImage(bi.getScaledInstance(bi.getWidth() / 2, bi.getHeight() / 2, Image.SCALE_SMOOTH), 0, 0, null);
                    ImageIO.write(thumb, extension, new File(entityType.getImgPath() + "thumb/" + fileName));
                }
            } catch (IOException ex) {
                throw new DbModelException(ex.getLocalizedMessage(), ex);
            }
        }
    }

    /**
     * Create a MimeMessage using the parameters provided.
     *
     * @param from Email address of the sender, the mailbox account.
     * @param to Email address of the receiver.
     * @param subject Subject of the email.
     * @param bodyText Body text of the email.
     * @return MimeMessage to be used to send email.
     * @throws MessagingException
     */
    public static MimeMessage createEmail(String from, String to, String subject,
            String bodyText) throws MessagingException {
        Properties props = new Properties();
        Session session = Session.getDefaultInstance(props, null);

        MimeMessage email = new MimeMessage(session);
        email.addRecipient(Message.RecipientType.CC, new InternetAddress(from));
        email.setFrom(new InternetAddress(from));
        email.addRecipient(javax.mail.Message.RecipientType.TO,
                new InternetAddress(to));
        email.setSubject(subject);
        email.setText(bodyText);
        return email;
    }

    /**
     * Create a MimeMessage using the parameters provided.
     *
     * @param from Email address of the sender, the mailbox account.
     * @param to Email address of the receiver.
     * @param subject Subject of the email.
     * @param bodyText Body text of the email.
     * @param fileDir Path to the directory containing attachment.
     * @param filename Name of file to be attached.
     * @return MimeMessage to be used to send email.
     * @throws MessagingException
     */
    public static MimeMessage createEmailWithAttachment(String from, String to, String subject,
            String bodyText, String fileDir, String filename) throws MessagingException, IOException {
        Properties props = new Properties();
        Session session = Session.getDefaultInstance(props, null);

        MimeMessage email = new MimeMessage(session);
        InternetAddress tAddress = new InternetAddress(to);
        InternetAddress fAddress = new InternetAddress(from);

        email.setFrom(fAddress);
        email.addRecipient(javax.mail.Message.RecipientType.TO, tAddress);
        email.setSubject(subject);

        MimeBodyPart mimeBodyPart = new MimeBodyPart();
        mimeBodyPart.setContent(bodyText, "text/plain");
        mimeBodyPart.setHeader("Content-Type", "text/plain; charset=\"UTF-8\"");

        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(mimeBodyPart);

        mimeBodyPart = new MimeBodyPart();
        DataSource source = new FileDataSource(fileDir + filename);

        mimeBodyPart.setDataHandler(new DataHandler(source));
        mimeBodyPart.setFileName(filename);
        String contentType = Files.probeContentType(FileSystems.getDefault()
                .getPath(fileDir, filename));
        mimeBodyPart.setHeader("Content-Type", contentType + "; name=\"" + filename + "\"");
        mimeBodyPart.setHeader("Content-Transfer-Encoding", "base64");

        multipart.addBodyPart(mimeBodyPart);

        email.setContent(multipart);

        return email;
    }

}
