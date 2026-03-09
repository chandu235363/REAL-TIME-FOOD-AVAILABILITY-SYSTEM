import java.util.*;

// ===================== CASE CLASS =====================
class CaseRecord {
    String caseId;
    String title;
    int priority; // For heap

    CaseRecord(String id, String title, int priority) {
        this.caseId = id;
        this.title = title;
        this.priority = priority;
    }

    void display() {
        System.out.println(caseId + " | " + title + " | Priority: " + priority);
    }
}

// ===================== CO2 - LINKED LIST =====================
class SinglyLinkedList {
    class Node {
        CaseRecord data;
        Node next;

        Node(CaseRecord data) {
            this.data = data;
        }
    }

    Node head;

    void insert(CaseRecord data) {
        Node newNode = new Node(data);
        newNode.next = head;
        head = newNode;
    }

    void display() {
        Node temp = head;
        while (temp != null) {
            temp.data.display();
            temp = temp.next;
        }
    }
}

// ===================== CO4 - CUSTOM HASH TABLE =====================
class HashTable {
    private LinkedList<CaseRecord>[] table;
    private int size = 10;

    HashTable() {
        table = new LinkedList[size];
        for (int i = 0; i < size; i++)
            table[i] = new LinkedList<>();
    }

    private int hash(String key) {
        return Math.abs(key.hashCode()) % size;
    }

    void insert(CaseRecord c) {
        int index = hash(c.caseId);
        table[index].add(c);
    }

    CaseRecord search(String id) {
        int index = hash(id);
        for (CaseRecord c : table[index]) {
            if (c.caseId.equals(id))
                return c;
        }
        return null;
    }
}

// ===================== CO3 - MAX HEAP =====================
class MaxHeap {
    private ArrayList<CaseRecord> heap = new ArrayList<>();

    void insert(CaseRecord c) {
        heap.add(c);
        heapifyUp(heap.size() - 1);
    }

    private void heapifyUp(int index) {
        while (index > 0) {
            int parent = (index - 1) / 2;
            if (heap.get(parent).priority < heap.get(index).priority) {
                Collections.swap(heap, parent, index);
                index = parent;
            } else
                break;
        }
    }

    CaseRecord extractMax() {
        if (heap.isEmpty())
            return null;

        CaseRecord max = heap.get(0);
        heap.set(0, heap.get(heap.size() - 1));
        heap.remove(heap.size() - 1);
        heapifyDown(0);

        return max;
    }

    private void heapifyDown(int index) {
        int largest = index;
        int left = 2 * index + 1;
        int right = 2 * index + 2;

        if (left < heap.size() &&
                heap.get(left).priority > heap.get(largest).priority)
            largest = left;

        if (right < heap.size() &&
                heap.get(right).priority > heap.get(largest).priority)
            largest = right;

        if (largest != index) {
            Collections.swap(heap, largest, index);
            heapifyDown(largest);
        }
    }
}

// ===================== MAIN SYSTEM =====================
public class JudicialFullDSA {

    static Scanner sc = new Scanner(System.in);

    static SinglyLinkedList caseList = new SinglyLinkedList();
    static Stack<CaseRecord> undoStack = new Stack<>();
    static Queue<String> visitorQueue = new LinkedList<>();
    static HashTable hashTable = new HashTable();
    static MaxHeap heap = new MaxHeap();

    static ArrayList<CaseRecord> caseArray = new ArrayList<>();

    public static void main(String[] args) {

        while (true) {
            System.out.println("\n===== FULL DSA JUDICIAL SYSTEM =====");
            System.out.println("1. Add Case");
            System.out.println("2. Search Case (Hashing)");
            System.out.println("3. Display All (Linked List)");
            System.out.println("4. Undo Last Case (Stack)");
            System.out.println("5. Add Visitor (Queue)");
            System.out.println("6. Process Priority Case (Heap)");
            System.out.println("7. Sort Cases (Merge Sort)");
            System.out.println("8. Linear Search");
            System.out.println("9. Binary Search");
            System.out.println("10. Exit");

            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    addCase();
                    break;
                case 2:
                    searchCase();
                    break;
                case 3:
                    caseList.display();
                    break;
                case 4:
                    undoCase();
                    break;
                case 5:
                    addVisitor();
                    break;
                case 6:
                    processPriority();
                    break;
                case 7:
                    mergeSort();
                    break;
                case 8:
                    linearSearch();
                    break;
                case 9:
                    binarySearch();
                    break;
                case 10:
                    System.exit(0);
            }
        }
    }

    static void addCase() {
        System.out.print("Case ID: ");
        String id = sc.nextLine();
        System.out.print("Title: ");
        String title = sc.nextLine();
        System.out.print("Priority (1-10): ");
        int priority = sc.nextInt();
        sc.nextLine();

        CaseRecord c = new CaseRecord(id, title, priority);

        caseList.insert(c); // Linked List
        undoStack.push(c); // Stack
        visitorQueue.add("Visitor for " + id); // Queue
        hashTable.insert(c); // Hashing
        heap.insert(c); // Heap
        caseArray.add(c); // For sorting/searching

        System.out.println("Case Added.");
    }

    static void searchCase() {
        System.out.print("Enter Case ID: ");
        String id = sc.nextLine();

        CaseRecord c = hashTable.search(id);
        if (c != null)
            c.display();
        else
            System.out.println("Not Found.");
    }

    static void undoCase() {
        if (undoStack.isEmpty()) {
            System.out.println("Nothing to undo.");
            return;
        }
        CaseRecord c = undoStack.pop();
        System.out.println("Removed: " + c.caseId);
    }

    static void addVisitor() {
        System.out.print("Visitor Name: ");
        String name = sc.nextLine();
        visitorQueue.add(name);
    }

    static void processPriority() {
        CaseRecord c = heap.extractMax();
        if (c != null)
            System.out.println("Processing Priority Case: " + c.caseId);
    }

    // CO1 - Merge Sort
    static void mergeSort() {
        caseArray.sort(Comparator.comparing(a -> a.caseId));
        System.out.println("Cases Sorted by Case ID.");
    }

    static void linearSearch() {
        System.out.print("Enter Case ID: ");
        String id = sc.nextLine();

        for (CaseRecord c : caseArray) {
            if (c.caseId.equals(id)) {
                c.display();
                return;
            }
        }
        System.out.println("Not Found.");
    }

    static void binarySearch() {
        System.out.print("Enter Case ID: ");
        String id = sc.nextLine();

        caseArray.sort(Comparator.comparing(a -> a.caseId));

        int left = 0, right = caseArray.size() - 1;

        while (left <= right) {
            int mid = (left + right) / 2;
            int cmp = caseArray.get(mid).caseId.compareTo(id);

            if (cmp == 0) {
                caseArray.get(mid).display();
                return;
            } else if (cmp < 0)
                left = mid + 1;
            else
                right = mid - 1;
        }
        System.out.println("Not Found.");
    }
}