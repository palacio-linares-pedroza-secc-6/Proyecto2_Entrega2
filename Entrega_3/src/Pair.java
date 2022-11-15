public class Pair {

    private int key;
    private Object value;

    public Pair(int key, Object value) {
        this.key = key;
        this.value = value;
    }
    /**
     * Retorna el primer valor de la pareja
     * @return El primer valor de la pareja, null si no hay nada
     */
    public int getKey() {
        return key;
    }
    public void setKey(int num){
        this.key = num;
    }
    /**
     * Retorna el segundo valor de la pareja
     * @return El segundo valor de la pareja, null si no hay nada
     */
    public Object getValue() {
        return value;
    }
}
