# AI Security with garak

A beginner-friendly companion project for learning **LLM security testing** with the official **NVIDIA garak** vulnerability scanner.

Rather than focusing only on theory, this repository provides hands-on examples that show how to scan local models, custom AI applications, and locally hosted LLMs using garak.

---

## 🙋 For Recruiters / Non-Technical Readers

**garak** is an open-source security scanner built by NVIDIA that automatically probes an AI chatbot or language model the way an attacker would — trying jailbreak personas, hidden/encoded instructions, and prompt-injection tricks to see if the model can be talked into ignoring its own safety rules or leaking information it shouldn't. An "LLM vulnerability scan" is essentially the AI equivalent of a network security scan: it fires a large, repeatable battery of known attack patterns at the model and reports exactly which ones succeeded, which failed, and why. This repository documents me running that scanner against three different kinds of AI targets — a downloaded open-source model, a REST API I built and deliberately gave a vulnerability, and a locally hosted LLM — and shows I can both configure a real security tool against a live system *and* read/interpret its findings, not just talk about AI risk in the abstract.

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

> **Windows users:** if a scan crashes with a `charmap`/`UnicodeEncodeError`, see [GUIDE.md → Troubleshooting](./GUIDE.md#11-troubleshooting) — it's a one-line console encoding fix, not a bug in the scan.

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

## 🔗 Related Work

This repo is one half of a broader AI security investigation:

- **[ai-security-gandalf](https://github.com/NehaKhann/ai-security-gandalf)** — a simplified rebuild of Lakera's Gandalf prompt-injection challenge, where I hand-crafted the attacks that try to talk a model into leaking a secret.
- **This repo (ai-security-garak)** — automates that same category of attack at scale using garak's probe library, instead of typing each prompt by hand.
- **Medium article — "From Gandalf to Garak — Automating the AI Attacks I Used to Type by Hand"** — walks through the throughline between the two projects: manual red-teaming first, then automating it with a real scanner.

Read together, the two repos show the same underlying skill (probing LLMs for prompt-injection and jailbreak failures) at two different levels of tooling: manual, then automated.

---

## 🖥️ Why No Live Demo?

There's no hosted URL to click for this project, and that's intentional rather than incomplete. garak is a **CLI security scanner**, not a web app — its output is a terminal run plus an HTML/JSON report, not a page you'd deploy. There's also nothing safe to host publicly: Example 2's chatbot is *deliberately vulnerable* by design, and Examples 1 and 3 scan models that need to run locally (local weights or a local Ollama server), not something a static host can serve.

The primary evidence here is the **scan output itself** — the terminal runs and the garak HTML/JSON reports checked into each example's `reports/` and `screenshots/` folders — the same artifacts you'd hand to a team after a real security assessment.

---

## 📖 References

- Official garak Repository: https://github.com/NVIDIA/garak
- Official Documentation: https://reference.garak.ai