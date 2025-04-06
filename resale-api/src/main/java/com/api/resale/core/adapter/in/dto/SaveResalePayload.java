package com.api.resale.core.adapter.in.dto;

import com.api.resale.infrastructure.exceptions.APICustomException;
import com.api.resale.infrastructure.exceptions.InvalidFieldException;
import com.api.resale.infrastructure.exceptions.InvalidFieldSizeException;
import com.api.resale.infrastructure.exceptions.MandatoryFieldException;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import static com.api.resale.core.utils.FormValidations.*;

@Data
@Slf4j
public class SaveResalePayload {

    private String cnpj;
    private String razaoSocial;
    private String nomeFantasia;
    private String email;
    private String telefone;
    private String nomeContato;
    private String enderecoEntrega;

    public void validate() {
        try {
            if (ehCampoNuloVazio(cnpj)) throw new MandatoryFieldException("cnpj");
            if (ehCampoNuloVazio(razaoSocial)) throw new MandatoryFieldException("razaoSocial");
            if (ehCampoNuloVazio(nomeFantasia)) throw new MandatoryFieldException("nomeFantasia");
            if (ehCampoNuloVazio(email)) throw new MandatoryFieldException("email");
            if (ehCampoNuloVazio(telefone)) throw new MandatoryFieldException("telefone");
            if (ehCampoNuloVazio(nomeContato)) throw new MandatoryFieldException("nomeContato");
            if (ehCampoNuloVazio(enderecoEntrega)) throw new MandatoryFieldException("enderecoEntrega");

            if (!isValidCNPJ(cnpj)) throw new InvalidFieldException("cnpj");
            if (!ehEmailValido(email)) throw new InvalidFieldException("email");
            if (!ehTelefoneValido(telefone)) throw new InvalidFieldException("telefone");
            if (!validarQtdCaracteres(razaoSocial, 2, 100)) throw new InvalidFieldSizeException("razaoSocial", 2, 100);
            if (!validarQtdCaracteres(nomeFantasia, 2, 100)) throw new InvalidFieldSizeException("nomeFantasia", 2, 100);
            if (!validarQtdCaracteres(nomeContato, 2, 100)) throw new InvalidFieldSizeException("nomeContato", 2, 100);
            if (!validarQtdCaracteres(enderecoEntrega, 5, 150)) throw new InvalidFieldSizeException("enderecoEntrega", 5, 150);

        } catch (InvalidFieldException | MandatoryFieldException e) {
            throw new APICustomException(e.getMessage());
        } catch (Exception e) {
            log.error("Error constructing payload: " + e.getMessage());
            throw new APICustomException();
        }
    }
}
