package com.evowork.utility;

import java.util.ArrayList;
import java.util.List;

import com.evowork.viewmodel.user.UserDTO;


/**
 * @author tuannd
 * @date 02/07/2016
 * @since 1.0
 */
public class BusinessUtility {
	
    /**
     * get user full name
     * @param firstName
     * @param lastName
     * @return
     * @since 1.0
     */
    public static String getUserFullname(String firstName, String lastName) {
        return StringUtility.isNotEmpty(firstName) ? firstName + " " + (StringUtility.isNotEmpty(lastName) ? lastName : "") : lastName;
    }

    /**
     * get user identifier
     * @param userDTO
     * @return
     * @since 1.0
     */
    public static String getUserIdentifier(UserDTO userDTO) {
        if (StringUtility.isNotEmpty(userDTO.getName())) {
            return userDTO.getName();
        }
        if (StringUtility.isNotEmpty(userDTO.getPhone())) {
            return userDTO.getPhone();
        }
        if (StringUtility.isNotEmpty(userDTO.getGgId())) {
            return userDTO.getGgId();
        }
        return userDTO.getId().toString();
    }

    private static String[] passwordSalts = {"A'Txc~#mrdjd2?PX;$W", "KNg&gt)ur6Ay$T", "gmU,@D5kz<?9?}'eV&%", ",F6]r&dfXBk=>#g[5E+u5p", "LjrjVA_fU<#hA~kg9^"};
    private static int[] insertSaltIndexs = {0, 4, 7, 11, 16, 21};
    public static String addSaltToPassword(String password) {
        /*Random random = new Random();
        String salt = passwordSalts[random.nextInt(passwordSalts.length)];
        int insertIndex = 999;
        int passwordLength = password.length();
        while (insertIndex > passwordLength) {
            insertIndex = insertSaltIndexs[random.nextInt(insertSaltIndexs.length)];
        }
        StringBuilder newPassword = new StringBuilder(password);
        newPassword.insert(Math.min(insertIndex, passwordLength), salt);
        return newPassword.toString();*/

        return password;
    }

    public static List<String> getAllPossiblePasswords(String rawPassword) {
        List<String> passwords = new ArrayList<>();
        int passwordLength = rawPassword.length();
        for (int i = 0; i < passwordSalts.length; i++) {
            String salt = passwordSalts[i];
            for (int j = 0; j < insertSaltIndexs.length; j++) {
                int index = insertSaltIndexs[j];
                StringBuilder password = new StringBuilder(rawPassword);
                password.insert(Math.min(index, passwordLength), salt);
                passwords.add(password.toString());
            }
            passwords.add((new StringBuilder(rawPassword)).insert(passwordLength, salt).toString());
        }
        return passwords;
    }
}
