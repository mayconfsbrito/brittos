/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * InternalFrameLogin.java
 *
 * Created on 14/09/2011, 14:46:25
 */
package gui.internalFrames;

import controle.objetos.seguranca.Transacao;
import gui.utils.ApagaElementosDaInterface;
import gui.utils.TamanhoJTextField;
import javax.swing.JOptionPane;
import persistencia.dao.seguranca.DAOUsuario;
import controle.utils.Criptografia;
import gui.FramePrincipal;
import persistencia.dao.seguranca.DAOTransacao;

/**
 *
 * @author Maycon Fernando Silva Brito
 * @email mayconfsbrito@gmail.com
 */
public class InternalFrameAlteraSenha extends gui.superclass.InternalFrame {

    private TamanhoJTextField limita = new TamanhoJTextField();
    private final int[] tamanhosJTextField = {45, 45};

    /** Creates new form InternalFrameLogin */
    public InternalFrameAlteraSenha() {
        initComponents();
        this.setVisible(true);
        this.inicializa();
    }

    @Override
    public void inicializa() {

        /*Inicializa o panel de mensagens*/
        textMensagem.setEditable(false);
        textMensagem.setText("Aqui você altera a sua senha.");

        /*Limita as TextFields*/
        this.limitaTextFields();

        /*Centraliza no DesktopPane*/
        super.centraliza();

        this.preencheLoginAutomatico();
    }

    @Override
    protected void limparGUI() {
        new ApagaElementosDaInterface().apagaJTextField(textConfirmaSenha, textNovaSenha, textSenha);
    }

    /**
     * Preenche o textLogin automaticamente a partir do usuario utilizado atualmente
     */
    private void preencheLoginAutomatico() {
        textLogin.setText(FramePrincipal.user.getLogin());
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
        buttonAlterar = new javax.swing.JButton();
        buttonLimpar = new javax.swing.JButton();
        buttonRedefinirSenha = new javax.swing.JButton();
        panelUsuario = new javax.swing.JPanel();
        labelLogin = new javax.swing.JLabel();
        labelSenha = new javax.swing.JLabel();
        textLogin = new javax.swing.JTextField();
        textSenha = new javax.swing.JPasswordField();
        labelNovaSenha = new javax.swing.JLabel();
        labelConfirmaSenha = new javax.swing.JLabel();
        textNovaSenha = new javax.swing.JPasswordField();
        textConfirmaSenha = new javax.swing.JPasswordField();
        panelMensagem = new javax.swing.JPanel();
        textMensagem = new javax.swing.JTextField();

        setClosable(true);
        setTitle("Alterar Senha");

        panelOpcoes.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Opções", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION));

        buttonCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/buttons/Cancelar.png"))); // NOI18N
        buttonCancelar.setText("Cancelar");
        buttonCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonCancelarActionPerformed(evt);
            }
        });

        buttonAlterar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/buttons/Confirma.png"))); // NOI18N
        buttonAlterar.setText("Alterar");
        buttonAlterar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonAlterarActionPerformed(evt);
            }
        });

        buttonLimpar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/buttons/Novo.png"))); // NOI18N
        buttonLimpar.setText("Limpar");
        buttonLimpar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonLimparActionPerformed(evt);
            }
        });

        buttonRedefinirSenha.setIcon(new javax.swing.ImageIcon(getClass().getResource("/buttons/Concluir.png"))); // NOI18N
        buttonRedefinirSenha.setText("Redefinir Senha");
        buttonRedefinirSenha.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonRedefinirSenhaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelOpcoesLayout = new javax.swing.GroupLayout(panelOpcoes);
        panelOpcoes.setLayout(panelOpcoesLayout);
        panelOpcoesLayout.setHorizontalGroup(
            panelOpcoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelOpcoesLayout.createSequentialGroup()
                .addComponent(buttonAlterar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(buttonLimpar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(buttonRedefinirSenha)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(buttonCancelar))
        );
        panelOpcoesLayout.setVerticalGroup(
            panelOpcoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelOpcoesLayout.createSequentialGroup()
                .addGroup(panelOpcoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(buttonAlterar)
                    .addComponent(buttonLimpar)
                    .addComponent(buttonCancelar)
                    .addComponent(buttonRedefinirSenha))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        panelUsuario.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Alterar Senha", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION));

        labelLogin.setText("Login:");

        labelSenha.setText("Senha atual:");

        textLogin.setEnabled(false);
        textLogin.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                textLoginFocusGained(evt);
            }
        });

        textSenha.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                textSenhaFocusGained(evt);
            }
        });

        labelNovaSenha.setText("Nova senha:");

        labelConfirmaSenha.setText("Confirmação da nova senha:");

        textNovaSenha.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                textNovaSenhaFocusGained(evt);
            }
        });

        textConfirmaSenha.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textConfirmaSenhaActionPerformed(evt);
            }
        });
        textConfirmaSenha.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                textConfirmaSenhaFocusGained(evt);
            }
        });

        javax.swing.GroupLayout panelUsuarioLayout = new javax.swing.GroupLayout(panelUsuario);
        panelUsuario.setLayout(panelUsuarioLayout);
        panelUsuarioLayout.setHorizontalGroup(
            panelUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelUsuarioLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelUsuarioLayout.createSequentialGroup()
                        .addComponent(labelConfirmaSenha)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(textConfirmaSenha, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, panelUsuarioLayout.createSequentialGroup()
                            .addComponent(labelNovaSenha)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(textNovaSenha))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, panelUsuarioLayout.createSequentialGroup()
                            .addComponent(labelSenha)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(textSenha))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, panelUsuarioLayout.createSequentialGroup()
                            .addComponent(labelLogin)
                            .addGap(36, 36, 36)
                            .addComponent(textLogin, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panelUsuarioLayout.setVerticalGroup(
            panelUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelUsuarioLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelLogin)
                    .addComponent(textLogin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelSenha)
                    .addComponent(textSenha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelNovaSenha)
                    .addComponent(textNovaSenha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelConfirmaSenha)
                    .addComponent(textConfirmaSenha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(21, Short.MAX_VALUE))
        );

        panelMensagem.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        textMensagem.setBackground(javax.swing.UIManager.getDefaults().getColor("Button.background"));
        textMensagem.setBorder(null);

        javax.swing.GroupLayout panelMensagemLayout = new javax.swing.GroupLayout(panelMensagem);
        panelMensagem.setLayout(panelMensagemLayout);
        panelMensagemLayout.setHorizontalGroup(
            panelMensagemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(textMensagem)
        );
        panelMensagemLayout.setVerticalGroup(
            panelMensagemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelMensagemLayout.createSequentialGroup()
                .addComponent(textMensagem)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelUsuario, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(panelMensagem, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(panelOpcoes, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(panelUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelOpcoes, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelMensagem, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

private void textLoginFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_textLoginFocusGained
    textMensagem.setText("Neste campo você o seu login.");
}//GEN-LAST:event_textLoginFocusGained

private void textSenhaFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_textSenhaFocusGained
    textMensagem.setText("Neste campo você digita a sua senha.");
}//GEN-LAST:event_textSenhaFocusGained

    @Override
protected void buttonCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonCancelarActionPerformed

        /**Caso não tenha algum usuário logado não fecha*/
        if (FramePrincipal.user != null) {
            FramePrincipal.buttonLogin.setEnabled(true);
            this.dispose();
        }
}//GEN-LAST:event_buttonCancelarActionPerformed

private void buttonAlterarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonAlterarActionPerformed

    /*Verifica se os campos estejam preenchidos*/
    if ((!textLogin.getText().isEmpty()) && (textSenha.getPassword().length > 0) && (textNovaSenha.getPassword().length > 0)
            && (textConfirmaSenha.getPassword().length > 0)) {

        /*Verifica se a senha está correta*/
        if (FramePrincipal.user.getSenha().equals(Criptografia.md5(new String(textSenha.getPassword())))) {
            /*Verifica se o texto digitado em Nova senha e em Confirma senha são iguais*/
            if (new String(textNovaSenha.getPassword()).equals(new String(textConfirmaSenha.getPassword()))) {
                /*Verifica se o usuário está ativo no sistema*/
                if (FramePrincipal.user.isAtivo()) {

                    /*Altera a senha do usuário no bd*/
                    FramePrincipal.user.setSenhaComMD5(new String(textNovaSenha.getPassword()));
                    new DAOUsuario().alterar(FramePrincipal.user);
                    DAOTransacao.inserir(new Transacao(FramePrincipal.user, "Alterou a sua senha " + FramePrincipal.user.getNome()));

                    this.limparGUI();
                    
                } else {
                    JOptionPane.showMessageDialog(this, "O usuário encontra-se inativo para utilizar este sistema.\n"
                            + "Favor contactar o administrador do sistema para poder utilizá-lo.",
                            "Usuário inativo!", JOptionPane.ERROR_MESSAGE);

                }

            } else {
                JOptionPane.showMessageDialog(this, "A senha digitada em Nova Senha e em Confirmação da Nova Senha são diferentes.", "Senha inválida!", JOptionPane.WARNING_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Senha atual incorreta. Digite novamente.", "Senha inválida!", JOptionPane.WARNING_MESSAGE);
        }

    } else {
        JOptionPane.showMessageDialog(this, "Por favor, preencha todos os campos.", "Preenchimento.", JOptionPane.WARNING_MESSAGE);
    }


}//GEN-LAST:event_buttonAlterarActionPerformed

    private void textNovaSenhaFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_textNovaSenhaFocusGained
        textMensagem.setText("Neste campo você digita a sua nova senha.");
    }//GEN-LAST:event_textNovaSenhaFocusGained

    private void textConfirmaSenhaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textConfirmaSenhaActionPerformed
        this.buttonAlterarActionPerformed(evt);
    }//GEN-LAST:event_textConfirmaSenhaActionPerformed

    private void textConfirmaSenhaFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_textConfirmaSenhaFocusGained
        textMensagem.setText("Neste campo você digita a sua senha novamente.");
    }//GEN-LAST:event_textConfirmaSenhaFocusGained

    private void buttonRedefinirSenhaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonRedefinirSenhaActionPerformed
        /**
         * Altera a senha do usuario atual para 123 e salva no bd
         */
        FramePrincipal.user.setSenhaComMD5("123");

        if (new DAOUsuario().alterar(FramePrincipal.user)) {
            JOptionPane.showMessageDialog(this, "A sua nova senha agora é 123.", "Alteração de senha.", JOptionPane.WARNING_MESSAGE);
            limparGUI();
        }
    }//GEN-LAST:event_buttonRedefinirSenhaActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton buttonAlterar;
    private javax.swing.JButton buttonCancelar;
    private javax.swing.JButton buttonLimpar;
    private javax.swing.JButton buttonRedefinirSenha;
    private javax.swing.JLabel labelConfirmaSenha;
    private javax.swing.JLabel labelLogin;
    private javax.swing.JLabel labelNovaSenha;
    private javax.swing.JLabel labelSenha;
    private javax.swing.JPanel panelMensagem;
    private javax.swing.JPanel panelOpcoes;
    private javax.swing.JPanel panelUsuario;
    private javax.swing.JPasswordField textConfirmaSenha;
    private javax.swing.JTextField textLogin;
    private javax.swing.JTextField textMensagem;
    private javax.swing.JPasswordField textNovaSenha;
    private javax.swing.JPasswordField textSenha;
    // End of variables declaration//GEN-END:variables
}
