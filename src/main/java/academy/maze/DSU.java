package academy.maze;

/**
 * Реализация системы непересекающихся множеств (DSU - Disjoint Set Union). Также известна как Union-Find. Используется
 * для эффективного управления и запроса связности элементов в динамически изменяющихся наборах.
 */
public class DSU {
    /**
     * Массив родительских элементов для каждого узла. parent[i] хранит родителя i-го элемента, для корня parent[i] == i
     */
    private final int[] parent;

    /** Массив рангов (высот) деревьев для каждого узла. Используется для оптимизации объединения множеств */
    private final int[] rank;

    /**
     * Создает DSU для указанного количества элементов. Инициализирует каждый элемент как отдельное множество.
     *
     * @param size количество элементов в системе
     */
    public DSU(int size) {
        parent = new int[size];
        rank = new int[size];
        for (int i = 0; i < size; i++) {
            parent[i] = i;
        }
    }

    /**
     * Находит корневой элемент множества, содержащего указанный элемент. Использует сжатие путей для оптимизации
     * последующих вызовов.
     *
     * @param value элемент, для которого ищется корень
     * @return корневой элемент множества
     */
    public int find(int value) {
        if (parent[value] != value) {
            parent[value] = find(parent[value]);
        }

        return parent[value];
    }

    /**
     * Объединяет множества, содержащие элементы x и y. Использует объединение по рангу для минимизации высоты дерева.
     * Если элементы уже находятся в одном множестве, операция не выполняется.
     *
     * @param x первый элемент
     * @param y второй элемент
     */
    public void union(int x, int y) {
        int rootX = find(x);
        int rootY = find(y);

        if (rootX != rootY) {
            if (rank[rootX] < rank[rootY]) {
                parent[rootX] = rootY;
            } else if (rank[rootX] > rank[rootY]) {
                parent[rootY] = rootX;
            } else {
                parent[rootY] = rootX;
                rank[rootX]++;
            }
        }
    }

    /**
     * Проверяет, находятся ли два элемента в одном множестве.
     *
     * @param x первый элемент
     * @param y второй элемент
     * @return {@code true} если элементы связаны, {@code false} в противном случае
     */
    public boolean connected(int x, int y) {
        return find(x) == find(y);
    }
}
