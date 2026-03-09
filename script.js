function showSection(event, id) {

    document.querySelectorAll("section")
        .forEach(sec => sec.classList.remove("active"));

    document.getElementById(id)
        .classList.add("active");

    document.querySelectorAll("nav button")
        .forEach(btn => btn.classList.remove("active-nav"));

    event.currentTarget.classList.add("active-nav");

    if (id === 'home') displayInventory();
}

function authenticate() {

    const id = document.getElementById("adminID").value;
    const pass = document.getElementById("adminPass").value;

    if (id === "2222" && pass === "1234")
        return true;

    alert("Invalid Admin Credentials");
    return false;
}

function addFoodItem() {

    if (!authenticate()) return;

    const id = document.getElementById("foodID").value;
    const name = document.getElementById("foodName").value;
    const restaurant = document.getElementById("foodRestaurant").value;
    const status = document.getElementById("foodStatus").value;
    const category = document.getElementById("foodCategory").value;

    let inventory = JSON.parse(localStorage.getItem("foodInventory")) || {};

    if (inventory[id]) {
        alert("A food item with this ID already exists.");
        return;
    }

    inventory[id] = { name, restaurant, status, category };

    localStorage.setItem("foodInventory", JSON.stringify(inventory));

    alert("Food added successfully");

    displayInventory();
}

function deleteFoodItem() {

    if (!authenticate()) return;

    const id = document.getElementById("deleteFoodID").value;
    let inventory = JSON.parse(localStorage.getItem("foodInventory")) || {};

    if (inventory.hasOwnProperty(id)) {
        delete inventory[id];
        localStorage.setItem("foodInventory", JSON.stringify(inventory));
        alert("Food item deleted successfully");
    } else {
        alert("Food item not found");
    }

    displayInventory();
}

function searchFood() {

    const id = document.getElementById("searchFoodID").value;

    let inventory = JSON.parse(localStorage.getItem("foodInventory")) || {};

    const result = document.getElementById("searchResult");

    if (inventory[id]) {

        result.innerHTML = "Item: " + inventory[id].name +
            "<br>Restaurant: " + inventory[id].restaurant +
            "<br>Status: " + inventory[id].status;

    } else {

        result.innerHTML = "Item not found";

    }
}

function displayInventory() {

    const inventory = JSON.parse(localStorage.getItem("foodInventory")) || {};

    const tbody = document.getElementById("foodListBody");

    tbody.innerHTML = "";

    let rows = "";

    for (let id in inventory) {

        rows += `<tr>
<td>${id}</td>
<td>${inventory[id].name}</td>
<td>${inventory[id].restaurant}</td>
<td>${inventory[id].status}</td>
<td>${inventory[id].category}</td>
</tr>`;
    }

    tbody.innerHTML = rows;
}

displayInventory();
