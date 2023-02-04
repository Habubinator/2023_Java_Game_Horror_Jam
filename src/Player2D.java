public class Player2D {
    int x = 0;
    int y = 310;
    int speed = 5;
    public void move(KeyHandler key){
        if (key.left_Pressed){
            x -= speed;
        } else
            if (key.right_Pressed) {
                x += speed;
        } else if (key.use_Pressed) {
                use();
            }
    }
    public void use(){

    }
}
