package novel;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {
    public boolean up_Pressed, down_Pressed, left_Pressed, right_Pressed, use_Pressed,exit,skip;
    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        switch (code) {
            case KeyEvent.VK_A -> left_Pressed = true;
            case KeyEvent.VK_W -> up_Pressed = true;
            case KeyEvent.VK_S -> down_Pressed = true;
            case KeyEvent.VK_D -> right_Pressed = true;
            case KeyEvent.VK_E -> use_Pressed = true;
            case KeyEvent.VK_ESCAPE -> exit = true;
            case KeyEvent.VK_NUMPAD0 -> skip = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();
        switch (code) {
            case KeyEvent.VK_A -> left_Pressed = false;
            case KeyEvent.VK_W -> up_Pressed = false;
            case KeyEvent.VK_S -> down_Pressed = false;
            case KeyEvent.VK_D -> right_Pressed = false;
            case KeyEvent.VK_E -> use_Pressed = false;
            case KeyEvent.VK_ESCAPE -> exit = false;
            case KeyEvent.VK_NUMPAD0 -> skip = false;
        }
    }
}
