# Beginner Guide to garak

*By Neha Khan* — companion to [NVIDIA/garak](https://github.com/NVIDIA/garak). All credit for garak itself goes to NVIDIA and its contributors; this is just a beginner-friendly walkthrough.

## Table of Contents

1. [What is garak?](#1-what-is-garak)
2. [Repo structure](#2-repo-structure)
3. [Core concepts](#3-core-concepts)
4. [Install & verify](#4-install--verify)
5. [Run your first scan](#5-run-your-first-scan)
6. [Reading the reports](#6-reading-the-reports)
7. [Hands-on: Hugging Face local model](./01-huggingface-local/README.md)
8. [Hands-on: Spring Boot REST chatbot](./02-springboot-rest/README.md)
9. [Hands-on: Ollama local model](./03-ollama-local/README.md)
10. [Adapting garak to your own app](#10-adapting-garak-to-your-own-app)
11. [Troubleshooting](#11-troubleshooting)

---

## 1. What is garak?

garak (**g**enerative **a**i **r**ed-teaming & **a**ssessment **k**it) is NVIDIA's open-source LLM vulnerability scanner — think **nmap for LLMs**. It scans a model or chatbot for jailbreaks, prompt injection, data leakage, toxic output, and other known failure modes. Run it *alongside* traditional security testing, since it covers the model/prompt layer regular scanners don't understand.

**Who it's for:** ML/AI engineers who want to catch a model's failure modes early, AppSec engineers who now have "an LLM" in scope, and beginners in AI red-teaming — garak ships dozens of ready-made probes, so you don't have to invent attacks yourself.

## 2. Repo structure

```
garak/
├── generators/   # how garak talks to a target (OpenAI, Hugging Face, REST, Ollama, NIM, ...)
├── probes/       # the attacks/prompts sent to the target
├── detectors/    # decide whether a response counts as a "fail"
├── evaluators/   # roll detector results up into a pass/fail rate per probe
├── harnesses/    # orchestrate probes -> generator -> detectors -> evaluator
├── buffs/        # optional prompt mutators (paraphrase, encode) before probes run
├── data/         # attack corpora, wordlists
└── configs/      # example YAML configs
```

Every plugin category has its own `base.py`, so once you understand one probe, you can read any of them.

## 3. Core concepts

| Concept | Role |
|---|---|
| **Generator** | Wraps the target being tested |
| **Probe** | Prompts designed to elicit one failure (e.g. `dan.Dan_11_0` = jailbreak persona) |
| **Detector** | Judges pass/fail on the response |
| **Evaluator** | Aggregates detector verdicts into a failure rate |
| **Harness** | Runs the whole loop and produces the report |
| **Buff** *(optional)* | Mutates prompts before sending, to test robustness |

A scan = **generator** + one or more **probes**, run through the default **harness**, scored by **detectors**, summarized by the **evaluator**. (There's no separate "Reporters" plugin type — the report files are just this pipeline's output.)

## 4. Install & verify

```bash
python3 -m venv venv
source venv/bin/activate       # Windows: venv\Scripts\activate
pip install -U garak
python3 -m garak --version     # no errors = you're set
```

> 💡 No need to clone the GitHub repo — `pip install garak` installs that same code for you. Only clone it if you want to read/modify the source or contribute.

> ⚠️ Newer garak uses `--target_type`/`--target_name`; older guides show `--model_type`/`--model_name`. Run `python3 -m garak --help` to check your version's flags.

## 5. Run your first scan

Instead of GPT-2, this guide uses **Qwen2.5-0.5B-Instruct** — small (~1GB) and instruction-tuned, so it can actually follow or refuse instructions, which is what most probes test for:

```bash
python3 -m garak --target_type huggingface --target_name Qwen/Qwen2.5-0.5B-Instruct --probes dan.Dan_11_0
```

garak downloads the model, sends each `dan.Dan_11_0` jailbreak prompt, records the response, checks it with the probe's detector, and prints a pass/fail table with a failure rate. Full walkthrough: [`01-huggingface-local/README.md`](./01-huggingface-local/README.md).

**Prefer a commercial model instead?** You can point garak at a hosted model like OpenAI's instead of downloading one yourself. Here's what each part means:

1. Get an API key (a private password that identifies your account) at [platform.openai.com/api-keys](https://platform.openai.com/api-keys).
2. `export OPENAI_API_KEY="..."` saves that key as an environment variable — a value your terminal remembers for the session, so garak can read it without you pasting it into every command.
3. Run garak, telling it to target OpenAI's API instead of a local model:

```bash
export OPENAI_API_KEY="sk-123XXXXXXXXXXXX"    # paste your real key here
python3 -m garak --target_type openai --target_name gpt-5-nano --probes encoding
```

- `--target_type openai` — use OpenAI's API instead of a local model
- `--target_name gpt-5-nano` — which OpenAI model to test (a small, cheap one)
- `--probes encoding` — test whether hiding a bad prompt in Base64/hex encoding sneaks it past the model's safety filters

Note: creating the key is free, but OpenAI no longer gives new accounts free trial credit — actually running the model needs a prepaid top-up ($5 minimum). If you'd rather not pay anything, stick with the free local `huggingface`/`ollama` examples above — no key or billing needed.

**Even easier — try `test` first, with zero setup.** Before touching a real model, garak has a built-in fake target for sanity-checking that everything's installed correctly:

```bash
python3 -m garak --target_type test.Blank --probes dan.Dan_11_0
```

No download, no API key — it just confirms garak, the probe, and the detector all run end-to-end. `test.Blank` always replies with an empty string; `test.Repeat` echoes the prompt back. Handy for learning the CLI before you point it at something real.

**Other targets garak supports:** once you're comfortable, garak can point at many more services the same way — just an env var for the key, and the right `--target_type`/`--target_name`:

| Service | `--target_type` | Needs |
|---|---|---|
| Hugging Face (hosted API) | `huggingface.InferenceAPI` | `HF_INFERENCE_TOKEN` (optional) |
| Replicate | `replicate` | `REPLICATE_API_TOKEN` |
| Cohere | `cohere` | `COHERE_API_KEY` |
| Groq | `groq` | `GROQ_API_KEY` |
| AWS Bedrock | `bedrock` | `BEDROCK_API_KEY` |
| NVIDIA NIM | `nim` | `NIM_API_KEY` |
| Local ggml/llama.cpp | `ggml` | `GGML_MAIN_PATH` |

Each works exactly like the OpenAI example above: set the env var, then run `garak --target_type <service> --target_name <model>`. Full details for each: [reference.garak.ai](https://reference.garak.ai).

## 6. Reading the reports

Each run writes files to `~/.local/share/garak/garak_runs/`, named by run UUID:

- **`<uuid>.report.jsonl`** — every prompt, response, and detector verdict (raw evidence)
- **`<uuid>.report.html`** — human-readable summary, failure rate per probe
- **`<uuid>.hitlog.jsonl`** — only the *failing* attempts — triage this first

## 10. Adapting garak to your own app

If your target isn't OpenAI/Hugging Face/Ollama, garak's [`rest` generator](https://github.com/NVIDIA/garak/tree/main/garak/generators) points at **any HTTP endpoint** via a short YAML config. [`02-springboot-rest/`](./02-springboot-rest/README.md) shows this end-to-end.

## 11. Troubleshooting

**`UnicodeEncodeError` / `'charmap' codec can't encode character ...` on Windows.**

garak prints emoji (📜 🕵️ ✔️) as status markers. Older Windows consoles (PowerShell 5.1, `cmd.exe`, Git Bash) default to a legacy code page like `cp1252`, which can't render them, and the whole run crashes before it prints anything useful. This is a console-encoding issue, not a bug in your scan — fix it once per session:

```powershell
$env:PYTHONIOENCODING = "utf-8"    # PowerShell
```

```bash
export PYTHONIOENCODING=utf-8      # Bash / Git Bash
```

Then re-run your `garak` command. (Windows Terminal with UTF-8 already set, or `chcp 65001`, works too.)

**Port 8080 already in use (Example 2).** Something else on your machine is already listening on 8080. Either stop that process, or run the chatbot on another port and point `rest-config.json`'s `uri` at it:

```bash
mvn spring-boot:run -Dspring-boot.run.arguments="--server.port=8090"
```

---

*garak is built and maintained by NVIDIA and its open-source contributors ([repo](https://github.com/NVIDIA/garak) · [docs](https://reference.garak.ai)). This is an independent companion guide, not official NVIDIA documentation.*