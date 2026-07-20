# AI Security with garak

A beginner-friendly companion project for learning **LLM security testing** with the official **NVIDIA garak** vulnerability scanner.

Rather than focusing only on theory, this repository provides hands-on examples that show how to scan local models, custom AI applications, and locally hosted LLMs using garak.

---

## 📖 Start Here

👉 **[GUIDE.md](./GUIDE.md)**

The guide introduces garak's core concepts, explains how it works, and links to each hands-on example.

---

## 📂 Repository Structure

```text
ai-security-garak/
│
├── GUIDE.md
├── 01-huggingface-local/
├── 02-springboot-rest/
└── 03-ollama-local/
```

- **GUIDE.md** — Introduction to garak, its architecture, and core concepts.
- **01-huggingface-local** — Scan a local Hugging Face model using Garak.
- **02-springboot-rest** — Scan a custom Spring Boot REST chatbot.
- **03-ollama-local** — Scan a locally hosted Ollama model.

---

## 🚀 Getting Started

Clone the repository:

```bash
git clone https://github.com/NehaKhann/ai-security-garak.git
cd ai-security-garak
```

Each example is self-contained and includes:

- A dedicated `README.md`
- Its own dependency setup
- A separate Python virtual environment (`venv`)
- Step-by-step instructions from setup to scanning

Simply open the example you want to explore and follow its README.

---

## 📦 Prerequisites

### Example 1 — Hugging Face

- Python 3.10+
- pip
- Internet connection (first model download)

### Example 2 — Spring Boot REST

- Python 3.10+
- Java 17+
- Maven

### Example 3 — Ollama

- Python 3.10+
- Ollama installed
- Approximately 5–8 GB of free disk space for local models

---

## 💡 Virtual Environments

Each example uses its **own Python virtual environment** to keep dependencies isolated.

Example:

```text
01-huggingface-local/
└── venv/

02-springboot-rest/
└── venv/

03-ollama-local/
└── venv/
```

Create and activate the virtual environment by following the instructions in the corresponding example's README.

---

## 🎯 What You'll Learn

By working through this repository, you'll learn how to:

- Install and configure garak
- Understand Generators, Probes, and Detectors
- Scan local Hugging Face models
- Scan custom REST-based AI applications
- Scan local Ollama models
- Interpret PASS, FAIL, and SKIP results
- Read Garak's HTML and JSON reports

---

## 📚 Learning Path

Start with **[GUIDE.md](./GUIDE.md)** to understand the core concepts, then work through the examples at your own pace.

Each example is independent, but following them in numerical order provides a gradual introduction to garak and its different generators.

---

## 📖 References

- Official garak Repository: https://github.com/NVIDIA/garak
- Official Documentation: https://reference.garak.ai