import javax.swing.JOptionPane;
import java.util.*;
import java.io.*;
/**
 * 
 */
public class Main
{
    private static final String VERSION = "jDump 0.2";
    
    public static final void main(String args[]) {
        try {
            
           
            
            final String INFILE = javax.swing.JOptionPane.showInputDialog(null, "infile", VERSION, javax.swing.JOptionPane.PLAIN_MESSAGE);
            final String OUTFILE = javax.swing.JOptionPane.showInputDialog(null, "outfile", VERSION, javax.swing.JOptionPane.PLAIN_MESSAGE);

            final boolean CHAR = (javax.swing.JOptionPane.showConfirmDialog(null, "dump ASCII?", VERSION, javax.swing.JOptionPane.YES_NO_OPTION) == javax.swing.JOptionPane.YES_OPTION);
            final boolean HEX = (javax.swing.JOptionPane.showConfirmDialog(null, "dump hex?", VERSION, javax.swing.JOptionPane.YES_NO_OPTION) == javax.swing.JOptionPane.YES_OPTION);
            final boolean BINARY = (javax.swing.JOptionPane.showConfirmDialog(null, "dump binary?", VERSION, javax.swing.JOptionPane.YES_NO_OPTION) == javax.swing.JOptionPane.YES_OPTION);
            final boolean ADDRESSES = (javax.swing.JOptionPane.showConfirmDialog(null, "dump addresses?", VERSION, javax.swing.JOptionPane.YES_NO_OPTION) == javax.swing.JOptionPane.YES_OPTION);

            final int BYTES_PER_LINE = Integer.parseInt(javax.swing.JOptionPane.showInputDialog(null, "bytes per line", VERSION, javax.swing.JOptionPane.PLAIN_MESSAGE));

            final java.io.FileInputStream in = new java.io.FileInputStream(INFILE);
            final java.io.PrintWriter out = new java.io.PrintWriter(OUTFILE);

            final int byte_accumulator[] = new int[BYTES_PER_LINE];
            int byte_ptr = 0, bytes_read = 0;
            for(;;) {
                final int data = in.read();
                if (data == -1) {
                    print(byte_accumulator, byte_ptr, bytes_read - (bytes_read % BYTES_PER_LINE), CHAR, HEX, BINARY, ADDRESSES, out);
                    break;
                } //EOF

                byte_accumulator[byte_ptr] = data;

                ++byte_ptr;
                ++bytes_read;

                if (byte_ptr == BYTES_PER_LINE) {
                    print(byte_accumulator, byte_ptr, bytes_read - (bytes_read % BYTES_PER_LINE) - BYTES_PER_LINE, CHAR, HEX, BINARY, ADDRESSES, out);
                    byte_ptr = 0;
                }
            }

            out.println("src: " + INFILE + " bytes: " + bytes_read);

            in.close();
            out.close();
        } catch (Exception e) {
            javax.swing.JOptionPane.showMessageDialog(null, "An exception occured:\n" + e, VERSION, javax.swing.JOptionPane.ERROR_MESSAGE);
        }
        javax.swing.JOptionPane.showMessageDialog(null, "Thank you for using " + VERSION + ".\n@author John Lekberg\n@version 2/28/2014", VERSION, javax.swing.JOptionPane.PLAIN_MESSAGE);
        System.exit(0);
    }

    private static final void print(final int[] byte_accumulator, final int byte_ptr, final int byte_line, final boolean CHAR, final boolean HEX, final boolean BINARY, final boolean ADDRESSES, final java.io.PrintWriter out) {
        for (int iter = byte_ptr; iter != byte_accumulator.length; ++iter) { byte_accumulator[iter] = -1; }
        if (CHAR) {
            for (int iter = 0; iter != byte_accumulator.length; ++iter) {
                if (byte_accumulator[iter] == -1) { out.print("--"); }
                else if (byte_accumulator[iter] <= 32 || byte_accumulator[iter] == 127) { out.print("  "); }
                else { out.print((char)byte_accumulator[iter] + " "); }
            }
            out.print("| ");
        }
        if (HEX) {
            for (int iter = 0; iter != byte_accumulator.length; ++iter) {
                if (byte_accumulator[iter] == -1) { out.print("---"); }
                else {
                    final String to_print = Integer.toHexString(byte_accumulator[iter]);
                    out.print((to_print.length() == 1 ? "0" : "") + to_print + " ");
                }
            }
            out.print("| ");
        }
        if (BINARY) {
            for (int iter = 0; iter != byte_accumulator.length; ++iter) {
                if (byte_accumulator[iter] == -1) { out.print("---------"); }
                else {
                    final String to_print = Integer.toBinaryString(byte_accumulator[iter]);
                    for (int zero_add = 0; zero_add != 8 - to_print.length(); ++zero_add) { out.print("0"); }
                    out.print(to_print + " ");
                }
            }
            out.print("| ");
        }
        if (ADDRESSES) {
            out.print("@" + byte_line);
        }

        out.println();
    }

    
}

