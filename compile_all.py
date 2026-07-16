import os
import subprocess
import glob
import shutil

WORK = r"C:\Users\1zyq1\Desktop\新建文件夹 (2)"
COMPILED_DIR = os.path.join(WORK, "compiled")
SPIGOT_API = r"C:\Users\1zyq1\.m2\repository\org\spigotmc\spigot-api\1.21.4-R0.1-SNAPSHOT\spigot-api-1.21.4-R0.1-SNAPSHOT.jar"
JAVA_HOME = r"C:\Program Files\Java\jdk-25.0.2"
JAVAC = os.path.join(JAVA_HOME, "bin", "javac.exe")
TEMP_SRC = r"C:\Users\1zyq1\AppData\Local\Temp\opencode\pe_src"
TEMP_OUT = r"C:\Users\1zyq1\AppData\Local\Temp\opencode\pe_out"

# Step 1: Copy src to temp dir (no Chinese in path) and strip BOM
print("=== Step 1: Prepare source files ===")
if os.path.exists(TEMP_SRC):
    shutil.rmtree(TEMP_SRC)
if os.path.exists(TEMP_OUT):
    shutil.rmtree(TEMP_OUT)

src_tree = os.path.join(WORK, "src_full")
shutil.copytree(src_tree, TEMP_SRC)

bom_count = 0
for root, dirs, files in os.walk(TEMP_SRC):
    for fname in files:
        if not fname.endswith(".java"):
            continue
        fpath = os.path.join(root, fname)
        with open(fpath, "rb") as f:
            raw = f.read()
        if raw.startswith(b"\xef\xbb\xbf"):
            with open(fpath, "wb") as f:
                f.write(raw[3:])
            bom_count += 1
print(f"  Copied to {TEMP_SRC}")
print(f"  Stripped {bom_count} BOMs")

# Step 2: Compile
print("\n=== Step 2: Compiling ===")
os.makedirs(TEMP_OUT, exist_ok=True)

java_files = glob.glob(os.path.join(TEMP_SRC, "**", "*.java"), recursive=True)
print(f"  Found {len(java_files)} Java files")

VAULT_API = os.path.join(WORK, "lib", "VaultAPI-1.7.jar")
cp = TEMP_SRC + ";" + WORK + "\\temp;" + SPIGOT_API + ";" + VAULT_API

cmd = [JAVAC, "--release", "16", "-encoding", "UTF-8", "-cp", cp, "-d", TEMP_OUT] + java_files
print("  Running javac...")
result = subprocess.run(cmd, capture_output=True)
stdout = result.stdout.decode("gbk", errors="replace") if result.stdout else ""
stderr = result.stderr.decode("gbk", errors="replace") if result.stderr else ""
if result.returncode == 0:
    print("  SUCCESS! All files compiled.")
    if stdout.strip():
        print(f"  stdout: {stdout.strip()}")
else:
    print(f"  FAILED (exit code {result.returncode})")
    if stderr.strip():
        lines = stderr.strip().split("\n")
        for line in lines[:30]:
            print(f"  {line}")
        if len(lines) > 30:
            print(f"  ... and {len(lines) - 30} more errors")

# Step 3: Copy compiled classes back
print(f"\n=== Step 3: Copy compiled classes back ===")
class_count = 0
for root, dirs, files in os.walk(TEMP_OUT):
    for f in files:
        if f.endswith(".class"):
            class_count += 1
            rel = os.path.relpath(os.path.join(root, f), TEMP_OUT)
            dest = os.path.join(COMPILED_DIR, rel)
            os.makedirs(os.path.dirname(dest), exist_ok=True)
            shutil.copy2(os.path.join(root, f), dest)
print(f"  Copied {class_count} .class files to compiled/")
