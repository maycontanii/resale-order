package com.api.resale.core.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FormValidations {

    public static final Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
    public static final Pattern VALID_PHONE_ADDRESS_REGEX = Pattern.compile("^\\(?(?:[14689][1-9]|2[12478]|3[1234578]|5[1345]|7[134579])\\)? ?(?:[2-8]|9[1-9])[0-9]{3}\\-?[0-9]{4}$", Pattern.CASE_INSENSITIVE);

    public static boolean ehTelefoneValido(String telefone) {
        Matcher matcher = VALID_PHONE_ADDRESS_REGEX.matcher(telefone);
        return matcher.find();
    }

    public static boolean validarQtdCaracteres(String campo, int maxCaracteres) {
        if (Objects.isNull(campo) || campo.isEmpty() || campo.isBlank()) return false;
        return campo.length() <= maxCaracteres;
    }

    public static boolean validarQtdCaracteres(String campo, int minCaracteres, int maxCaracteres) {
        if (Objects.isNull(campo) || campo.isEmpty() || campo.isBlank()) return false;
        return campo.length() >= minCaracteres && campo.length() <= maxCaracteres;
    }

    public static boolean ehEmailValido(String email) {
        if (Objects.isNull(email) || email.isEmpty()) return false;
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(email);
        return matcher.find();
    }

    public static boolean ehCampoNulo(Object campo) {
        return Objects.isNull(campo);
    }

    public static boolean ehCampoNuloVazio(String campo) {
        return Objects.isNull(campo) || campo.isEmpty() || campo.isBlank();
    }

    public static boolean isValidCNPJ(String cnpj) {
        // Remove caracteres não numéricos
        cnpj = cnpj.replaceAll("[^\\d]", "");

        // Verifica se tem 14 dígitos
        if (cnpj.length() != 14 || cnpj.matches("(\\d)\\1{13}")) {
            return false;
        }

        // Validação dos dígitos verificadores
        int[] pesos1 = {5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};
        int[] pesos2 = {6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};

        int soma = 0;
        for (int i = 0; i < 12; i++) {
            soma += (cnpj.charAt(i) - '0') * pesos1[i];
        }

        int digito1 = soma % 11 < 2 ? 0 : 11 - (soma % 11);

        soma = 0;
        for (int i = 0; i < 13; i++) {
            soma += (cnpj.charAt(i) - '0') * pesos2[i];
        }

        int digito2 = soma % 11 < 2 ? 0 : 11 - (soma % 11);

        return cnpj.endsWith("" + digito1 + digito2);
    }

    public static boolean ehDataFormatoErrado(String data) {
        try {
            if (Objects.isNull(data)) return true;

            DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
            df.setLenient(false);
            df.parse(data);
            return false;
        } catch (ParseException e) {
            return true;
        }
    }

    public static LocalDate convertStringToDate(String data) {
        try {
            DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
            df.setLenient(false);
            Date date = df.parse(data);
            return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        } catch (Exception e) {
            return null;
        }
    }

    public static boolean ehDataFormatoErrado(String pattern, String data) {
        try {
            DateFormat df = new SimpleDateFormat(pattern);
            df.setLenient(false);
            df.parse(data);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }
}
