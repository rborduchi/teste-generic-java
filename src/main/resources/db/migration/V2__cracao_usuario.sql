CREATE TABLE teste_generic.tb_funcionario (
        id UUID,
        nome VARCHAR(100),
        sobrenome VARCHAR(100),
        endereco VARCHAR(200),
        data_nascimento DATE,
        CONSTRAINT tb_funcionario_pkey PRIMARY KEY (id)
);

CREATE TABLE teste_generic.tb_cliente (
        id UUID,
        nome VARCHAR(100),
        sobrenome VARCHAR(100),
        endereco VARCHAR(200),
        data_nascimento DATE,
        CONSTRAINT tb_cliente_pkey PRIMARY KEY (id)
);