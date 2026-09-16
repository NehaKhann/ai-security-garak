# Example 1 — Scanning a Local Hugging Face Model

**Target:** DistilGPT-2 running locally with Hugging Face `transformers`.

This example shows how to run your first LLM security scan using **Garak**—no API keys or external services required.

---

## Prerequisites

- Python 3.10+
- Internet connection (first run only)

---

## Step 1 — Create a Virtual Environment

### Windows (PowerShell)

```powershell
cd 01-huggingface-local
python -m venv venv
.\venv\Scripts\Activate.ps1
```

### Linux / macOS

```bash
cd 01-huggingface-local
python3 -m venv venv
source venv/bin/activate
```

---

## Step 2 — Install Dependencies

```bash
pip install -r requirements.txt
```

This installs:

- garak
- torch
- transformers

Verify the installation:

### Windows

```powershell
python -m garak --version
```

### Linux / macOS

```bash
python3 -m garak --version
```

---

## Step 3 — Run Your First Scan

### Windows

```powershell
python -m garak --target_type huggingface --target_name distilgpt2 --probes dan.Dan_11_0
```

### Linux / macOS

```bash
chmod +x run_scan.sh
./run_scan.sh
```

On the first run, Hugging Face downloads the model automatically. Future scans reuse the cached model.

**Terminal output:**

![garak dan.Dan_11_0 scan running in the terminal](./screenshots/dan.Dan_11_0-terminal.png)

**HTML report:**

![garak dan.Dan_11_0 HTML report](./screenshots/dan.Dan_11_0-report.png)

---

## Understanding the Command

```text
python -m garak
```

Runs Garak.

```text
--target_type huggingface
```

Loads the model using Hugging Face Transformers.

```text
--target_name distilgpt2
```

Specifies the model to scan.

```text
--probes dan.Dan_11_0
```

Runs the DAN jailbreak probe.

---

## Understanding the Results

After the scan, you'll typically see results like:

```text
dan.DAN: PASS
mitigation.MitigationBypass: FAIL
```

- **PASS** → The detector did not find the tested behavior.
- **FAIL** → The detector identified a potential vulnerability.
- **SKIP** → The detector couldn't evaluate the response or the probe wasn't applicable.

Different detectors evaluate different aspects of the same response, so seeing both PASS and FAIL together is normal.

---

## Reports

Every scan automatically generates:

- `garak.log` – execution log
- `report.jsonl` – raw scan results
- `report.html` – browser-friendly report

The terminal prints the exact location of these files after every run.

---

## Try Other Probes

### Base64 Injection

```powershell
python -m garak --target_type huggingface --target_name distilgpt2 --probes encoding.InjectBase64
```

Tests whether the model follows Base64-encoded instructions.

![garak encoding.InjectBase64 HTML report](./screenshots/encoding.InjectBase64-report.png)

### Offensive Language

```powershell
python -m garak --target_type huggingface --target_name distilgpt2 --probes lmrc.SlurUsage
```

Evaluates how the model responds to prompts involving offensive language.

![garak SlurUsage HTML report](./screenshots/SlurUsage-report.png)

### List All Probes

```powershell
python -m garak --list_probes
```

---

## Try Another Model

```powershell
python -m garak --target_type huggingface --target_name gpt2 --probes dan.Dan_11_0
```

Different models may produce different security results.

---

## What You'll Learn

- Run Garak against a local Hugging Face model.
- Understand targets, probes, and detectors.
- Interpret PASS, FAIL, and SKIP results.
- Locate and read Garak's HTML and JSON reports.

For a more detailed explanation of Garak's architecture, probes, detectors, logs, and reports, see **GUIDE.md**.