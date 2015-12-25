package com.benoj.openpass1pass;

import org.junit.Test;

import java.io.File;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.groups.Tuple.tuple;

public class OpenPassVaultTest {

    @Test
    public void canGetPasswordsFromContentFile() throws Exception {
        String testVaultPath = getClass().getResource("testKeychain").getPath();
        OpenPassVault vault = new OpenPassVault(testVaultPath);
        List<OpenPassword> passwords = vault.getPasswords();

        assertThat(passwords)
                .extracting((password) -> tuple(password.getName(), password.getType(), password.getFile()))
                .containsExactly
                        (tuple("Password",OpenPasswordType.ONE_PASSWORD_PASSWORD, new File(testVaultPath + "/data/default/9B67B0387D7C4F8FB3C8EF3456E09C71.1password")));
    }
}

