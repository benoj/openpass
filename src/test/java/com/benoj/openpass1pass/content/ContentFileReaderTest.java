package com.benoj.openpass1pass.content;

import com.benoj.openpass1pass.OpenPassword;
import com.benoj.openpass1pass.OpenPasswordType;
import com.google.gson.Gson;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.security.SecureRandom;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.groups.Tuple.tuple;

public class ContentFileReaderTest {

    @Rule
    public TemporaryFolder folder = new TemporaryFolder();

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    private File contentsFile;
    private Gson gson;
    private ContentFileReader contentFileReader;

    @Before
    public void setUp() throws Exception {
        contentsFile = folder.newFile("contents.js");
        contentFileReader = new ContentFileReader(contentsFile);
        gson = new Gson();
    }

    @Test
    public void throwsFileNotFoundExceptionWhenGivenIncorrectFileLocation() throws Exception {
        expectedException.expect(FileNotFoundException.class);
        new ContentFileReader(new File("/noneexistant/contents.js"));
    }

    @Test
    public void throwsIOExceptionWhenFileCannotBeRead() throws Exception {
        expectedException.expect(IOException.class);
        Files.delete(contentsFile.toPath());
        contentFileReader.getPasswords();
    }

    @Test
    public void readsFromDiskWhenFileUpdated() throws Exception {
      Object[][] content = {
                new Object[]{generateHexUUID(), "passwords.Password", UUID.randomUUID().toString(), "",  1450308619, "", 0, "N"},

        };
        writeContentToFile(content);

        assertThat(contentFileReader.getPasswords().size()).isEqualTo(1);
        Object[][] content2 = {
                new Object[]{generateHexUUID(), "passwords.ONE_PASSWORD_PASSWORD", UUID.randomUUID().toString(), "",  1450308619, "", 0, "N"},
                new Object[]{generateHexUUID(), "passwords.ONE_PASSWORD_PASSWORD", UUID.randomUUID().toString(), "",  1450308619, "", 0, "N"},

        };
        writeContentToFile(content2);

        assertThat(contentFileReader.getPasswords().size()).isEqualTo(2);
    }


    @Test
    public void canDeserializePasswordType() throws Exception {
        String firstUUID = generateHexUUID();
        String secondUUID = generateHexUUID();
        String firstName = UUID.randomUUID().toString();
        String secondName = UUID.randomUUID().toString();

        Object[][] content = {
                new Object[]{firstUUID, "passwords.Password", firstName, "",  1450308619, "", 0, "N"},
                new Object[]{secondUUID, "passwords.Password", secondName, "",  1450308234, "", 1, "Y"},

        };
        writeContentToFile(content);


        List<OpenPassword> passwords = contentFileReader.getPasswords();
        assertThat(passwords)
                .extracting(password -> tuple(password.getName(), password.getFile(), password.getType()))
                .containsExactly(
                    tuple(firstName, filePathFor(firstUUID), OpenPasswordType.ONE_PASSWORD_PASSWORD),
                    tuple(secondName, filePathFor(secondUUID), OpenPasswordType.ONE_PASSWORD_PASSWORD)
                );

    }

    private void writeContentToFile(Object[][] content) throws IOException {
        try(FileWriter writer = new FileWriter(contentsFile)) {
            String jsonContent = gson.toJson(content);
            writer.write(jsonContent);
        }
    }

    private File filePathFor(String uuid) {
        return new File(contentsFile.getParent() + "/" + uuid + ".1password");
    }

    private String generateHexUUID() {
        byte[] uuid = new byte[16];
        new SecureRandom().nextBytes(uuid);
        StringBuilder sb = new StringBuilder();
        for (byte b : uuid) {
            sb.append(String.format("%02X", b));
        }
        return sb.toString();
    }
}