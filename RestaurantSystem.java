import java.util.*;

class Food {
    int id;
    String name;

    Food(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public String toString() {
        return id + " | " + name;
    }
}

/*
 * =========================
 * CO2 – Singly Linked List
 * =========================
 */
class Node {
    Food data;
    Node next;

    Node(Food data) {
        this.data = data;
    }
}

class SinglyLinkedList {
    Node head;

    void insert(Food food) {
        Node newNode = new Node(food);
        if (head == null)
            head = newNode;
        else {
            Node temp = head;
            while (temp.next != null)
                temp = temp.next;
            temp.next = newNode;
        }
    }

    void delete(int id) {
        if (head == null)
            return;

        if (head.data.id == id) {
            head = head.next;
            return;
        }

        Node temp = head;
        while (temp.next != null && temp.next.data.id != id)
            temp = temp.next;

        if (temp.next != null)
            temp.next = temp.next.next;
    }

    void search(int id) {
        Node temp = head;
        while (temp != null) {
            if (temp.data.id == id) {
                System.out.println("Found in LinkedList: " + temp.data);
                return;
            }
            temp = temp.next;
        }
        System.out.println("Not Found");
    }

    void display() {
        Node temp = head;
        while (temp != null) {
            System.out.println(temp.data);
            temp = temp.next;
        }
    }
}

/*
 * =========================
 * CO4 – Hashing (Chaining)
 * =========================
 */
class HashTable {
    LinkedList<Food>[] table;
    int size;

    HashTable(int size) {
        this.size = size;
        table = new LinkedList[size];
        for (int i = 0; i < size; i++)
            table[i] = new LinkedList<>();
    }

    int hash(int key) {
        return key % size;
    }

    void insert(Food food) {
        table[hash(food.id)].add(food);
    }

    Food search(int id) {
        for (Food f : table[hash(id)])
            if (f.id == id)
                return f;
        return null;
    }
}

/*
 * =========================
 * MAIN CLASS
 * =========================
 */
public class RestaurantSystem {

    // CO1 – ArrayList ADT
    static ArrayList<Food> foods = new ArrayList<>();

    // CO2 – Linked List
    static SinglyLinkedList list = new SinglyLinkedList();

    // CO3 – Stack (Array)
    static Stack<Food> stack = new Stack<>();

    // CO3 – Queue (Linked List)
    static Queue<Food> queue = new LinkedList<>();

    // CO4 – Hash Table
    static HashTable hashTable = new HashTable(10);

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\n===== Restaurant Food Management System =====");
            System.out.println("1. Add Food Item (Admin)");
            System.out.println("2. View All Food Items");
            System.out.println("3. Remove Food Item");
            System.out.println("4. Search Food Item by ID");
            System.out.println("5. Sort Food Items by ID");
            System.out.println("6. View Last Added Food (Stack)");
            System.out.println("7. View First Added Food (Queue)");
            System.out.println("8. Fast Search Food (Hash Search)");
            System.out.println("9. Exit");

            choice = sc.nextInt();

            switch (choice) {

                case 1:
                    System.out.print("Enter ID: ");
                    int id = sc.nextInt();
                    sc.nextLine();

                    System.out.print("Enter Food Name: ");
                    String name = sc.nextLine();

                    Food food = new Food(id, name);

                    foods.add(food);
                    list.insert(food);
                    stack.push(food);
                    queue.add(food);
                    hashTable.insert(food);

                    System.out.println("Food Added Successfully.");
                    break;

                case 2:
                    if (foods.isEmpty()) {
                        System.out.println("No food items available.");
                    } else {
                        for (Food f : foods)
                            System.out.println(f);
                    }
                    break;

                case 3:
                    System.out.print("Enter ID to remove: ");
                    int removeId = sc.nextInt();

                    Food toRemove = null;

                    for (Food f : foods) {
                        if (f.id == removeId) {
                            toRemove = f;
                            break;
                        }
                    }

                    if (toRemove != null) {
                        foods.remove(toRemove);
                        list.delete(removeId);
                        System.out.println("Food removed successfully.");
                    } else {
                        System.out.println("Food not found.");
                    }
                    break;

                case 4:
                    System.out.print("Enter ID to search: ");
                    linearSearch(sc.nextInt());
                    break;

                case 5:
                    bubbleSort();
                    break;

                case 6:
                    if (!stack.isEmpty())
                        System.out.println("Last Added Food: " + stack.peek());
                    else
                        System.out.println("No food items.");
                    break;

                case 7:
                    if (!queue.isEmpty())
                        System.out.println("First Added Food: " + queue.peek());
                    else
                        System.out.println("No food items.");
                    break;

                case 8:
                    System.out.print("Enter ID to search (Fast): ");
                    Food result = hashTable.search(sc.nextInt());
                    if (result != null)
                        System.out.println("Found: " + result);
                    else
                        System.out.println("Food not found.");
                    break;

                case 9:
                    System.out.println("Exiting...");
                    break;

                default:
                    System.out.println("Invalid choice!");
            }

        } while (choice != 9);

        sc.close();
    }

    /*
     * =========================
     * CO1 – Linear Search
     * =========================
     */
    static void linearSearch(int id) {
        for (Food f : foods) {
            if (f.id == id) {
                System.out.println("Found (O(n)): " + f);
                return;
            }
        }
        System.out.println("Not Found");
    }

    /*
     * =========================
     * CO1 – Binary Search
     * =========================
     */
    static void binarySearch(int id) {
        foods.sort(Comparator.comparingInt(f -> f.id));
        int left = 0, right = foods.size() - 1;

        while (left <= right) {
            int mid = (left + right) / 2;

            if (foods.get(mid).id == id) {
                System.out.println("Found (O(log n)): " + foods.get(mid));
                return;
            } else if (foods.get(mid).id < id)
                left = mid + 1;
            else
                right = mid - 1;
        }
        System.out.println("Not Found");
    }

    /*
     * =========================
     * CO1 – Bubble Sort
     * =========================
     */
    static void bubbleSort() {
        int n = foods.size();
        for (int i = 0; i < n - 1; i++)
            for (int j = 0; j < n - i - 1; j++)
                if (foods.get(j).id > foods.get(j + 1).id)
                    Collections.swap(foods, j, j + 1);

        System.out.println("Sorted using Bubble Sort (O(n^2))");
    }
}