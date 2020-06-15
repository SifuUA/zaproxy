/*
 * Zed Attack Proxy (ZAP) and its related class files.
 *
 * ZAP is an HTTP/HTTPS proxy for assessing web application security.
 *
 * Copyright 2016 The ZAP Development Team
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.zaproxy.zap.extension.pscan;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.*;
import org.parosproxy.paros.Constant;
import org.parosproxy.paros.model.OptionsParam;
import org.parosproxy.paros.view.AbstractParamPanel;
import org.zaproxy.zap.utils.I18N;
import org.zaproxy.zap.utils.ZapNumberSpinner;
import org.zaproxy.zap.view.LayoutHelper;

/**
 * The GUI panel for options of the passive scanner.
 *
 * <p>It allows to change the following options:
 *
 * <ul>
 *   <li>Scan only in scope - allows to set if the passive scan should be performed only on messages
 *       that are in scope.
 * </ul>
 *
 * @since 2.6.0
 */
class PassiveScannerContentSecurityPolicyDifferenceFinderOptionsPanel extends AbstractParamPanel {

    private static final long serialVersionUID = 1L;

    private JTextField overridesFilename;

    public PassiveScannerContentSecurityPolicyDifferenceFinderOptionsPanel(I18N messages) {
        setName(messages.getString("pscan.options.csp.title"));


        JButton overridesButton =
                new JButton(
                        Constant.messages.getString("pscan.options.csp.button.overridesFilename"));
        overridesButton.addActionListener(new FileChooserAction(getOverridesFilename()));

        JLabel overridesLabel = new JLabel("File with reference Content Security Policy");
        overridesLabel.setLabelFor(overridesButton);

        JPanel overridesPanel = new JPanel(new FlowLayout(FlowLayout.LEADING, 0, 0));
        overridesPanel.add(getOverridesFilename());
        overridesPanel.add(overridesButton);

        this.setLayout(new GridBagLayout());

        int y = 0;

        this.add(overridesLabel, LayoutHelper.getGBC(0, ++y, 3, 1.0, new Insets(2, 2, 2, 2)));
        this.add(overridesPanel, LayoutHelper.getGBC(1, ++y, 2, 1.0, new Insets(2, 2, 2, 2)));
        this.add(new JLabel(""), LayoutHelper.getGBC(0, ++y, 2, 1.0, 1.0));
    }

    @Override
    public void initParam(Object obj) {
        OptionsParam optionsParam = (OptionsParam) obj;

    }

    @Override
    public void saveParam(Object obj) throws Exception {
        OptionsParam optionsParam = (OptionsParam) obj;
    }

    @Override
    public String getHelpIndex() {
        return "ui.dialogs.options.pscan.main";
    }

    private static class FileChooserAction implements ActionListener {

        private final JTextField textField;

        public FileChooserAction(JTextField bindTextField) {
            this.textField = bindTextField;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            JFileChooser fileChooser = new JFileChooser();
            String path = textField.getText();
            if (path != null) {
                File file = new File(path);
                if (file.canRead() && !file.isDirectory()) {
                    fileChooser.setSelectedFile(file);
                }
            }
            if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                final File selectedFile = fileChooser.getSelectedFile();

                textField.setText(selectedFile.getAbsolutePath());
            }
        }
    }

    private JTextField getOverridesFilename() {
        if (overridesFilename == null) {
            overridesFilename = new JTextField(20);
        }
        return overridesFilename;
    }
}
