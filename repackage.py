import os
import shutil
import subprocess

WORK = r"C:\Users\1zyq1\Desktop\新建文件夹 (2)"
TEMP = os.path.join(WORK, "temp")
COMPILED = os.path.join(WORK, "compiled")
JAR_ORIG = os.path.join(WORK, "PrismEnchants.jar")
JAR_NEW = os.path.join(WORK, "PrismEnchants-中文.jar")

# Step 1: Replace ALL .class files from compiled/ into temp/
replaced = 0
for root, dirs, files in os.walk(COMPILED):
    for fname in files:
        if fname.endswith(".class"):
            rel = os.path.relpath(os.path.join(root, fname), COMPILED)
            dst = os.path.join(TEMP, rel)
            os.makedirs(os.path.dirname(dst), exist_ok=True)
            shutil.copy2(os.path.join(root, fname), dst)
            replaced += 1

print("Step 1: Replaced " + str(replaced) + " .class files in temp/")

# Step 2: Also update lang/ and config.yml in temp/
lang_dst_dir = os.path.join(TEMP, "lang")
os.makedirs(lang_dst_dir, exist_ok=True)
for lang_file in ["zh.yml", "en.yml"]:
    lang_src = os.path.join(WORK, "lang", lang_file)
    lang_dst = os.path.join(lang_dst_dir, lang_file)
    shutil.copy2(lang_src, lang_dst)
print("Step 2: Copied lang/zh.yml + en.yml to temp/lang/")

config_src = os.path.join(WORK, "config.yml")
config_dst = os.path.join(TEMP, "config.yml")
shutil.copy2(config_src, config_dst)
print("Step 3: Copied config.yml to temp/")

# Step 3: Repackage JAR
print("Step 4: Creating " + JAR_NEW + " ...")
original_dir = os.getcwd()
os.chdir(TEMP)
# jar cf creates the JAR from the current directory
cmd = [r"C:\Program Files\Java\jdk-25.0.2\bin\jar.exe", "cf", JAR_NEW, "."]
result = subprocess.run(cmd, capture_output=True)
if result.returncode == 0:
    jar_size = os.path.getsize(JAR_NEW)
    print("SUCCESS! Created " + JAR_NEW + " (" + str(jar_size) + " bytes)")
else:
    stderr = result.stderr.decode("gbk", errors="replace")
    print("FAILED: " + stderr)

os.chdir(original_dir)

# Step 4: Verify the new JAR contents
print("\nStep 5: Verifying JAR contents...")
cmd2 = [r"C:\Program Files\Java\jdk-25.0.2\bin\jar.exe", "tf", JAR_NEW]
result2 = subprocess.run(cmd2, capture_output=True)
if result2.returncode == 0:
    listing = result2.stdout.decode("gbk", errors="replace")
    # Count key files
    enchant_types = [l for l in listing.split("\n") if "enchant/types/" in l and l.endswith(".class")]
    lang_files = [l for l in listing.split("\n") if "lang/" in l]
    print("  Enchant .class files: " + str(len(enchant_types)))
    print("  Lang files: " + str(lang_files))
    # Check config.yml
    has_config = any("config.yml" in l for l in listing.split("\n"))
    print("  Has config.yml: " + str(has_config))
