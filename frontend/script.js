// -------------------- AUTH CHECK --------------------
const user = localStorage.getItem("loggedUser");
if (!user) {
  location.href = "index.html";
}

// -------------------- NOTIFICATION SETUP --------------------
const notifiedTasks = new Set();

// -------------------- DOM ELEMENTS --------------------
const title = document.getElementById("title");
const time = document.getElementById("time");
const list = document.getElementById("task-list");
const addBtn = document.getElementById("add-btn");
const logoutBtn = document.getElementById("logoutBtn");

// -------------------- LOAD TASKS --------------------
if ("serviceWorker" in navigator) {
  navigator.serviceWorker.register("sw.js")
    .then(() => console.log("Service Worker registered"))
    .catch(err => console.error("SW error", err));
}

function loadTasks() {
  fetch(`http://localhost:8080/api/tasks/${user}`)
    .then(res => res.json())
    .then(render)
    .catch(err => console.error("Load error:", err));
}

// -------------------- ADD TASK --------------------
if ("Notification" in window && Notification.permission !== "granted") {
  Notification.requestPermission().then(p => {
    console.log("Notification permission:", p);
  });
}

function addTask() {
  if (!title.value || !time.value) {
    alert("Enter task and time");
    return;
  }

  fetch(`http://localhost:8080/api/tasks/${user}`, {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify({
      title: title.value,
      time: time.value
    })
  })
    .then(res => {
      if (!res.ok) throw new Error("Failed to add task");
      title.value = "";
      time.value = "";
      loadTasks();
    })
    .catch(err => console.error("Add error:", err));
}

// -------------------- DELETE TASK --------------------
function deleteTask(id) {
  fetch(`http://localhost:8080/api/tasks/${user}/${id}`, {
    method: "DELETE"
  })
    .then(res => {
      if (!res.ok) throw new Error("Delete failed");
      loadTasks();
    })
    .catch(err => console.error("Delete error:", err));
}

// -------------------- RENDER TASKS --------------------
function render(tasks) {
  list.innerHTML = "";

  tasks.forEach(task => {
    const li = document.createElement("li");
    li.className = "task-item";

    const span = document.createElement("span");
    span.textContent = `${task.time} - ${task.title}`;

    const btn = document.createElement("button");
    btn.className = "delete-btn";
    btn.textContent = "Delete";

    btn.addEventListener("click", () => {
      deleteTask(task.id);
    });

    li.appendChild(span);
    li.appendChild(btn);
    list.appendChild(li);
  });
}

// -------------------- NOTIFICATION LOGIC --------------------
function showNotification(message) {
  if (Notification.permission !== "granted") return;

  navigator.serviceWorker.getRegistration().then(reg => {
    if (reg) {
      reg.showNotification("Task Reminder", {
        body: message,
        icon: "https://cdn-icons-png.flaticon.com/512/1827/1827504.png",
        badge: "https://cdn-icons-png.flaticon.com/512/1827/1827504.png",
        requireInteraction: true // 👈 stays in panel
      });
    }
  });
}

setInterval(() => {
  fetch(`http://localhost:8080/api/tasks/${user}`)
    .then(res => res.json())
    .then(tasks => {
      const now = new Date();

      tasks.forEach(task => {
        if (!task.time || notifiedTasks.has(task.id)) return;

        const [hh, mm] = task.time.split(":").map(Number);
        const taskTime = new Date();
        taskTime.setHours(hh, mm, 0, 0);

        const diffSec = (now - taskTime) / 1000;

        if (diffSec >= 0 && diffSec <= 120) {
          showNotification(task.title);
          notifiedTasks.add(task.id);
        }
      });
    });
}, 15000);

// -------------------- EVENT BINDINGS --------------------
addBtn.onclick = addTask;

logoutBtn.onclick = () => {
  localStorage.removeItem("loggedUser");
  location.href = "index.html";
};

// -------------------- INITIAL LOAD --------------------
loadTasks();
