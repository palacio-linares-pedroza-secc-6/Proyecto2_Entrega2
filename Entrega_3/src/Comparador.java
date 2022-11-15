import java.util.Comparator;

public class Comparador implements Comparator<Pair> {
    @Override
    public int compare(Pair jug1, Pair jug2) {

        if (jug1.getKey() < jug2.getKey()) {
            return 1;
        }

        else if (jug1.getKey() > jug2.getKey()) {
            return -1;
        }
        else{
            return 0;
        }
    }

}