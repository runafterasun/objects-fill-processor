package objects.fill.core;

public enum GlobalParameters {

    /**
     * Количество объектов созданных в коллекции.
     */
    objectCount(5),

    /**
     * Количество символов для случайной генерации.
     */
    valueLength(5),

    /**
     * Глубина дерева классов.
     */
    fillDeep(3);

    private Integer value;

    GlobalParameters(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }

    @SuppressWarnings("unused")
    public void setValue(Integer value) {
        this.value = value;
    }
}
