package battleship.javaproj;

//This is a String queue with finite capacity
public class RegistryQueue {
    private int size;
    private int elements;
    private String[] queue;

    public RegistryQueue(int size) {
        elements = 0;
        this.size = size;
        queue = new String[size];
    }

    public void push(String s) {
        if (elements == size) {
            for (int i = 0; i < size-1; i++) queue[i] = queue[i+1];
            queue[size-1] = s;
        }
        else queue[elements++] = s;
    }

    public String getAll(boolean reverse) {
        String out = "";
        if (!reverse) {
            for (int i = 0; i < elements; i++) {
                out += String.valueOf(i) + ". " + queue[i] + "\n";
            }
            return out;
        }
        else {
            for (int i = elements-1; i >= 0; i--) {
                out += String.valueOf(elements - i) + ". " + queue[i] + "\n";
            }
            return out;
        }
    }
}