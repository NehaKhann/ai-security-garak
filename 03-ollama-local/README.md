# Example 3 — Scanning a Local Ollama Model

**Target:** A locally running open-source LLM (e.g., Llama 3) using **Ollama**.

Unlike Example 1, the model is served by **Ollama** instead of Hugging Face. Garak connects directly to Ollama's local API, so no configuration file is required.

---

## Prerequisites

- Ollama installed
- Garak installed (see Example 1)
- Approximately 5–8 GB of free disk space for the model

---

# Step 1 — Install Ollama

Download Ollama from:

https://ollama.com/download

### Linux

```bash
curl -fsSL https://ollama.com/install.sh | sh
```

### Windows / macOS

Download and run the installer.

---

# Step 2 — Download a Model

### Windows (PowerShell)

```powershell
ollama pull llama3
```

### Linux / macOS

```bash
ollama pull llama3
```

This downloads the model once and stores it locally.

> **Tip:** If your machine has limited memory or disk space, consider using a smaller model such as `phi3` or `gemma2:2b`.

---

# Step 3 — Verify Ollama is Running

### Windows (PowerShell)

```powershell
curl.exe http://localhost:11434
```

### Linux / macOS

```bash
curl http://localhost:11434
```

Expected output:

```text
Ollama is running
```

If Ollama isn't running, start it manually:

```bash
ollama serve
```

Leave this terminal open while running Garak.

---

# Step 4 — Run Garak

### Windows (PowerShell)

```powershell
python -m garak --target_type ollama --target_name llama3 --probes dan.Dan_11_0
```

### Linux / macOS

```bash
python3 -m garak --target_type ollama --target_name llama3 --probes dan.Dan_11_0
```

Or use the provided script:

```bash
chmod +x run_scan.sh
./run_scan.sh
```

---

# What Happens During the Scan?

Garak:

1. Connects to Ollama's local API (`http://localhost:11434`).
2. Sends jailbreak prompts to the model.
3. Collects the model's responses.
4. Uses detectors to evaluate whether the responses are safe.
5. Generates HTML and JSON reports.

Unlike Example 2, Garak communicates directly with Ollama, so no `rest-config.json` file is required.

---

# Try Other Probes

### Base64 Injection

```powershell
python -m garak --target_type ollama --target_name llama3 --probes encoding.InjectBase64
```

### Offensive Language

```powershell
python -m garak --target_type ollama --target_name llama3 --probes lmrc.SlurUsage
```

### List Available Probes

```powershell
python -m garak --list_probes
```

---

# View the Report

After every scan, Garak generates:

```text
garak.log
garak.<uuid>.report.jsonl
garak.<uuid>.report.html
```

Open the HTML report in your browser to review the scan summary.

---

# Key Takeaways

- Learned how to scan a locally hosted Ollama model.
- Understood how Garak connects directly to Ollama's API.
- Ran jailbreak and security probes against a real LLM.
- Learned where Garak stores HTML and JSON reports.
- Compared scanning an Ollama model with Hugging Face models and REST-based AI applications.