/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * InternalFrameLogin.java
 *
 * Created on 14/09/2011, 14:46:25
 */
package br.com.areiasbrittos.gui.internalFrames;

import br.com.areiasbrittos.gui.utils.TamanhoJTextField;
import java.sql.Date;
import java.util.List;
import javax.swing.JOptionPane;
import br.com.areiasbrittos.controle.objetos.seguranca.Usuario;
import br.com.areiasbrittos.persistencia.dao.seguranca.DAOUsuario;
import br.com.areiasbrittos.controle.utils.Criptografia;
import br.com.areiasbrittos.gui.FramePrincipal;

/**
 *
 * @author Maycon Fernando Silva Brito
 * @email mayconfsbrito@gmail.com
 */
public class InternalFrameLogin extends br.com.areiasbrittos.gui.superclass.InternalFrame {

    private TamanhoJTextField limita = new TamanhoJTextField();
    private final int[] tamanhosJTextField = {45, 45};

    /** Creates new form InternalFrameLogin */
    public InternalFrameLogin() {
        initComponents();
        this.setVisible(true);
        this.inicializa();
    }

    @Override
    public void inicializa() {

        /*Inicializa o panel de mensagens*/
        textMensagem.setEditable(false);
        textMensagem.setText("Faça o login para usar o sistema.");

        /*Limita as TextFields*/
        this.limitaTextFields();

        /*Configura o comportamento*/
        FramePrincipal.buttonLogin.setEnabled(false);
        if (FramePrincipal.user == null) {
            buttonCancelar.setEnabled(false);
        } else {
            buttonCancelar.setEnabled(true);
        }

        /*Centraliza no DesktopPane*/
        super.centraliza();
    }

    @Override
    protected void limparGUI() {
        textLogin.setText("");
        textSenha.setText("");
    }

    private void limitaTextFields() {
        /**Limita a quantidade de caracteres possíveis nas JTextFields**/
        limita.limitaTamanho(tamanhosJTextField, textLogin, textSenha);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelOpcoes = new javax.swing.JPanel();
        buttonCancelar = new javax.swing.JButton();
        buttonLogar = new javax.swing.JButton();
        buttonLimpar = new javax.swing.JButton();
        panelUsuario = new javax.swing.JPanel();
        labelLogin = new javax.swing.JLabel();
        labelSenha = new javax.swing.JLabel();
        textLogin = new javax.swing.JTextField();
        textSenha = new javax.swing.JPasswordField();
        labelImagem = new javax.swing.JLabel();
        panelMensagem1 = new javax.swing.JPanel();
        textMensagem = new javax.swing.JTextField();

        setTitle("Login");

        panelOpcoes.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Opções", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION));

        buttonCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/buttons/Cancelar.png"))); // NOI18N
        buttonCancelar.setText("Cancelar");
        buttonCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonCancelarActionPerformed(evt);
            }
        });

        buttonLogar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/buttons/Confirma.png"))); // NOI18N
        buttonLogar.setText("Logar");
        buttonLogar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonLogarActionPerformed(evt);
            }
        });

        buttonLimpar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/buttons/Novo.png"))); // NOI18N
        buttonLimpar.setText("Limpar");
        buttonLimpar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonLimparActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelOpcoesLayout = new javax.swing.GroupLayout(panelOpcoes);
        panelOpcoes.setLayout(panelOpcoesLayout);
        panelOpcoesLayout.setHorizontalGroup(
            panelOpcoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelOpcoesLayout.createSequentialGroup()
                .addComponent(buttonLogar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(buttonLimpar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(buttonCancelar))
        );
        panelOpcoesLayout.setVerticalGroup(
            panelOpcoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelOpcoesLayout.createSequentialGroup()
                .addGroup(panelOpcoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(buttonLogar)
                    .addComponent(buttonLimpar)
                    .addComponent(buttonCancelar))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        panelUsuario.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Usuário", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION));

        labelLogin.setText("Login:");

        labelSenha.setText("Senha:");

        textLogin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textLoginActionPerformed(evt);
            }
        });
        textLogin.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                textLoginFocusGained(evt);
            }
        });

        textSenha.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textSenhaActionPerformed(evt);
            }
        });
        textSenha.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                textSenhaFocusGained(evt);
            }
        });

        labelImagem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/panels/Usuario seguro.png"))); // NOI18N

        javax.swing.GroupLayout panelUsuarioLayout = new javax.swing.GroupLayout(panelUsuario);
        panelUsuario.setLayout(panelUsuarioLayout);
        panelUsuarioLayout.setHorizontalGroup(
            panelUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelUsuarioLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(labelLogin)
                    .addComponent(labelSenha))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(textSenha)
                    .addComponent(textLogin, javax.swing.GroupLayout.DEFAULT_SIZE, 115, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(labelImagem)
                .addContainerGap(30, Short.MAX_VALUE))
        );
        panelUsuarioLayout.setVerticalGroup(
            panelUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelUsuarioLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(labelImagem)
                    .addGroup(panelUsuarioLayout.createSequentialGroup()
                        .addGroup(panelUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(labelLogin)
                            .addComponent(textLogin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panelUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(labelSenha)
                            .addComponent(textSenha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(31, Short.MAX_VALUE))
        );

        panelMensagem1.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        textMensagem.setBackground(javax.swing.UIManager.getDefaults().getColor("Button.background"));
        textMensagem.setBorder(null);

        javax.swing.GroupLayout panelMensagem1Layout = new javax.swing.GroupLayout(panelMensagem1);
        panelMensagem1.setLayout(panelMensagem1Layout);
        panelMensagem1Layout.setHorizontalGroup(
            panelMensagem1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(textMensagem, javax.swing.GroupLayout.DEFAULT_SIZE, 301, Short.MAX_VALUE)
        );
        panelMensagem1Layout.setVerticalGroup(
            panelMensagem1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelMensagem1Layout.createSequentialGroup()
                .addComponent(textMensagem)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelUsuario, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(panelOpcoes, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(panelMensagem1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(panelUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelOpcoes, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelMensagem1, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

private void textLoginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textLoginActionPerformed
    this.buttonLogarActionPerformed(evt);
}//GEN-LAST:event_textLoginActionPerformed

private void textLoginFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_textLoginFocusGained
    textMensagem.setText("Neste campo você digita a sua senha.");
}//GEN-LAST:event_textLoginFocusGained

private void textSenhaFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_textSenhaFocusGained
    textMensagem.setText("Neste campo você digita o seu usuário.");
}//GEN-LAST:event_textSenhaFocusGained

    @Override
protected void buttonCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonCancelarActionPerformed

        /**Caso não tenha algum usuário logado não fecha*/
        if (FramePrincipal.user != null) {
            FramePrincipal.buttonLogin.setEnabled(true);
            this.dispose();
        }
}//GEN-LAST:event_buttonCancelarActionPerformed

private void buttonLogarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonLogarActionPerformed

    /*Caso os campos estejam preenchidos*/
    if ((!textLogin.getText().isEmpty()) && (textSenha.getPassword().length > 0)) {

        /*Recupera os dados deste usuário no bd*/
        DAOUsuario daoUser = new DAOUsuario();
        List<Usuario> listUsuario = daoUser.consultar("login like '" + textLogin.getText() + "'");

        /*Verifica se existe algum usuário com este login no bd*/
        if (listUsuario.size() > 0) {
            /*Verifica se a senha está correta*/
            if (listUsuario.get(0).getSenha().equals(Criptografia.md5(new String(textSenha.getPassword())))) {
                /*Verifica se o usuário está ativo no sistema*/
                if (listUsuario.get(0).isAtivo()) {
                    /*Verifica se a validade do usuário expirou*/
                    Date data = new Date(System.currentTimeMillis());
                    if (listUsuario.get(0).getValidade().after(data)) {
                        /*Conecta o usuário ao sistema*/
                        if (FramePrincipal.conectaUsuario(listUsuario.get(0))) {

                            /*Inicializa acessos para o usuário em @FramePrincipal*/
                            if (FramePrincipal.verificaAcessos()) {
                                /*Fecha este frame*/
                                limparGUI();
                                buttonCancelarActionPerformed(evt);
                                FramePrincipal.buttonLogin.setEnabled(false);
                                FramePrincipal.buttonLogoff.setEnabled(true);

                            } else {
                                JOptionPane.showMessageDialog(this, "Não foi possível inicializar os acessos do usuário a partir de suas pemissões.\n"
                                        + "Favor contactar o administrador do sistema.",
                                        "Erro!", JOptionPane.ERROR_MESSAGE);
                            }

                        } else {
                            JOptionPane.showMessageDialog(this, "Erro ao realizar a conexão do usuário.\n"
                                    + "Favor contactar o administrador do sistema.",
                                    "Erro!", JOptionPane.ERROR_MESSAGE);
                        }
                    } else {
                        JOptionPane.showMessageDialog(this, "A validade deste usuário para utilizar o sistema expirou.\n"
                            + "Favor contactar o administrador do sistema para poder utilizá-lo novamente.",
                            "Usuário inativo!", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "O usuário encontra-se inativo para utilizar este sistema.\n"
                            + "Favor contactar o administrador do sistema para poder utilizá-lo novamente.",
                            "Usuário inativo!", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Senha incorreta. Tente novamente ou contacte o administrador do sistema.", "Senha inválida!", JOptionPane.ERROR_MESSAGE);
                textSenha.setText("");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Usuário inválido!", "Usuário inválido!", JOptionPane.ERROR_MESSAGE);
        }
    } else {
        JOptionPane.showMessageDialog(this, "Por favor, preencha o usuário e a senha.", "Usuário.", JOptionPane.ERROR_MESSAGE);
    }


}//GEN-LAST:event_buttonLogarActionPerformed

private void textSenhaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textSenhaActionPerformed
    this.buttonLogarActionPerformed(evt);
}//GEN-LAST:event_textSenhaActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton buttonCancelar;
    private javax.swing.JButton buttonLimpar;
    private javax.swing.JButton buttonLogar;
    private javax.swing.JLabel labelImagem;
    private javax.swing.JLabel labelLogin;
    private javax.swing.JLabel labelSenha;
    private javax.swing.JPanel panelMensagem1;
    private javax.swing.JPanel panelOpcoes;
    private javax.swing.JPanel panelUsuario;
    private javax.swing.JTextField textLogin;
    private javax.swing.JTextField textMensagem;
    private javax.swing.JPasswordField textSenha;
    // End of variables declaration//GEN-END:variables
}
