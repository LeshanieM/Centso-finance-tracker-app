Centso - 💰 Personal Finance Tracker App

A mobile application developed in **Kotlin** as part of **IT2010 – Mobile Application Development** (BSc (Hons) in Information Technology – Year 2, Faculty of Computing, SLIIT).

This app allows users to track their **income, expenses**, and **savings** easily, manage monthly budgets, and gain insights into their spending habits.

## 📱 Features

### 🔄 Transaction Management
- Add, edit, and delete **income** and **expense** transactions
- Each transaction includes:
  - Title
  - Amount
  - Category (e.g., Food, Transport, Bills)
  - Date

### 📊 Category-wise Spending Analysis
- Categorize each transaction
- View summary reports of spending **by category**

### 💸 Monthly Budget Setup
- Set a **monthly budget**
- Track budget usage with visual cues
- Receive **warnings** when nearing or exceeding the budget

### 💾 Data Persistence
- Stores transaction history and user preferences using **SharedPreferences**
- Data persists across app restarts

---

## 🎁 Bonus Features

### 📤 Data Backup & Restore
- Export transactions as `.txt` or `.json` file using **internal storage**
- Restore from saved backup files

### 🔔 Push Notifications
- Notify user when approaching/exceeding budget
- Optional daily reminders to log expenses

---

## 🛠️ Technologies & Tools

- **Kotlin** for Android development
- **Android Studio** (with XML layout design)
- **SharedPreferences** for local data storage
- **Internal Storage** for backup/restore
- **NotificationManager** for budget alerts
