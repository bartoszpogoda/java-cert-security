package app;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.file.Paths;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import com.github.bartoszpogoda.learning.CryptographyProvider;

public class MainFrame {

	private File toEnc, doDec;

	private JFrame frame;
	private JTextField tfFileToEnc;
	private JTextField tfFileToDec;
	private JTextField tfKey;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainFrame window = new MainFrame();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public MainFrame() {
		initialize();
	}

	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setBackground(new Color(255, 250, 250));
		frame.setBounds(100, 100, 474, 225);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		tfFileToEnc = new JTextField();
		tfFileToEnc.setEditable(false);
		tfFileToEnc.setBounds(28, 99, 149, 28);
		frame.getContentPane().add(tfFileToEnc);
		tfFileToEnc.setColumns(10);

		JButton btnChooseFileToEncrypt = new JButton("...");
		btnChooseFileToEncrypt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				toEnc = chooseFileDialog();

				if (toEnc != null) {
					tfFileToEnc.setText(toEnc.getName());
				}
			}
		});
		btnChooseFileToEncrypt.setBounds(179, 99, 40, 28);
		frame.getContentPane().add(btnChooseFileToEncrypt);

		tfFileToDec = new JTextField();
		tfFileToDec.setEditable(false);
		tfFileToDec.setBounds(245, 99, 143, 28);
		frame.getContentPane().add(tfFileToDec);
		tfFileToDec.setColumns(10);

		JButton btnChooseFileTo = new JButton("...");
		btnChooseFileTo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				doDec = chooseFileDialog();

				if (doDec != null) {
					tfFileToDec.setText(doDec.getName());
				}
			}
		});
		btnChooseFileTo.setBounds(389, 99, 40, 28);
		frame.getContentPane().add(btnChooseFileTo);

		tfKey = new JTextField();
		tfKey.setText("abcdefghijklmopr");
		tfKey.setBounds(28, 38, 401, 48);
		frame.getContentPane().add(tfKey);
		tfKey.setColumns(10);

		JButton btnEncrypt = new JButton("Encrypt");
		btnEncrypt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (toEnc == null)
					return;

				String key = tfKey.getText();
				File inputFile = toEnc;

				String encryptedFileName = Paths.get(inputFile.getParent(), "enc-" + inputFile.getName())
						.toAbsolutePath().toString();
				File encryptedFile = new File(encryptedFileName);

				try {
					FileInputStream inputStream = new FileInputStream(inputFile);
					byte[] inputBytes = new byte[(int) inputFile.length()];
					inputStream.read(inputBytes);

					byte[] encode = CryptographyProvider.withSecretKey(key).encode(inputBytes);

					FileOutputStream outputStream = new FileOutputStream(encryptedFile);
					outputStream.write(encode);

					inputStream.close();
					outputStream.close();

				} catch (Exception ex) {
					ex.printStackTrace();
				}

			}
		});
		btnEncrypt.setBounds(28, 135, 191, 23);
		frame.getContentPane().add(btnEncrypt);

		JButton btnDecrypt = new JButton("Decrypt");
		btnDecrypt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (doDec == null)
					return;

				String key = tfKey.getText();
				File inputFile = doDec;

				String decryptedFileName = Paths.get(inputFile.getParent(), "dec-" + inputFile.getName())
						.toAbsolutePath().toString();
				File decryptedFile = new File(decryptedFileName);

				try {
					FileInputStream inputStream = new FileInputStream(inputFile);
					byte[] inputBytes = new byte[(int) inputFile.length()];
					inputStream.read(inputBytes);

					byte[] encode = CryptographyProvider.withSecretKey(key).decode(inputBytes);

					FileOutputStream outputStream = new FileOutputStream(decryptedFile);
					outputStream.write(encode);

					inputStream.close();
					outputStream.close();
				} catch (Exception ex) {
					ex.printStackTrace();
				}

			}
		});
		btnDecrypt.setBounds(245, 135, 185, 23);
		frame.getContentPane().add(btnDecrypt);

		JLabel lblEncryptionKey = new JLabel("ENCRYPTION KEY");
		lblEncryptionKey.setFont(new Font("SimSun-ExtB", Font.PLAIN, 15));
		lblEncryptionKey.setBounds(179, 13, 120, 14);
		frame.getContentPane().add(lblEncryptionKey);
	}

	private File chooseFileDialog() {
		JFileChooser jfc = new JFileChooser(Paths.get("").toAbsolutePath().normalize().toString());

		int returnValue = jfc.showOpenDialog(null);

		if (returnValue == JFileChooser.APPROVE_OPTION) {
			return jfc.getSelectedFile();
		}

		return null;
	}
}
