# Hướng dẫn mod XP Boost (Minecraft 1.20.1 + Forge 47.4.10)

## Mod làm gì?

Thêm enchant **Tăng Kinh Nghiệm / Experience Boost**:

| Cấp | Hệ số XP khi giết mob |
|-----|------------------------|
| I   | x2                     |
| II  | x4                     |
| III | x6                     |
| IV  | x8                     |
| V   | x10                    |

- Chỉ ép được vào **kiếm**
- Không xuất hiện ở bàn phù phép
- Chỉ mua sách enchant từ **dân làng thủ thư (Librarian)**
- Dùng đe (Anvil) để ép sách vào kiếm

---

## Bước 1 — Cài JDK 17 (đã hỗ trợ cài trên máy bạn)

Forge 1.20.1 **bắt buộc** Java 17 (không dùng Java 8/11).

Nếu cần cài lại: https://adoptium.net/temurin/releases/?version=17  
Chọn: Windows → x64 → JDK → `.msi`

Sau khi cài, mở **PowerShell mới** và kiểm tra:

```powershell
java -version
```

Phải thấy dòng giống: `openjdk version "17.x.x"`

---

## Bước 2 — Mở project bằng IntelliJ IDEA (khuyên dùng)

1. Tải IntelliJ IDEA Community (miễn phí): https://www.jetbrains.com/idea/download/
2. Mở IntelliJ → **Open** → chọn thư mục `D:\Test`
3. IntelliJ nhận file `build.gradle` → chọn **Open as Project** / **Trust Project**
4. Đợi Gradle tải thư viện (lần đầu có thể 5–20 phút, cần mạng)

Nếu bị hỏi JDK: chọn JDK 17 vừa cài.

---

## Bước 3 — Tạo cấu hình chạy game trong IntelliJ

Mở Terminal trong IntelliJ (hoặc PowerShell tại `D:\Test`) rồi chạy:

```powershell
.\gradlew.bat genIntellijRuns
```

Sau đó trong IntelliJ: chuột phải project → **Reload Gradle Project**.

Bạn sẽ thấy các run config: `runClient`, `runServer`, ...

---

## Bước 4 — Chạy thử trong môi trường dev

1. Chọn **runClient**
2. Bấm Run (tam giác xanh)
3. Game mở ra → tạo Singleplayer world
4. Kiểm tra nhanh bằng lệnh (cheats ON):

```text
/give @s enchanted_book{StoredEnchantments:[{id:"xpboost:experience_boost",lvl:5s}]} 1
```

5. Lấy kiếm + đe, ép sách vào kiếm
6. Giết vài zombie/skeleton → XP sẽ nhiều hơn bình thường

---

## Bước 5 — Build file .jar để chơi bình thường

Trong thư mục `D:\Test` chạy:

```powershell
.\gradlew.bat build
```

File mod nằm ở:

```text
D:\Test\build\libs\xpboost-1.0.0.jar
```

Copy file `.jar` vào:

```text
%appdata%\.minecraft\mods
```

(Hoặc thư mục mods của profile Forge 1.20.1 bạn đang dùng)

Khởi động Minecraft bằng **Forge 1.20.1 (47.4.10)** → vào Mods list phải thấy **XP Boost**.

---

## Bước 6 — Cách lấy enchant trong chơi thật (không dùng lệnh)

1. Tìm / nuôi dân làng **thủ thư** (Lectern)
2. Trade emerald lấy **Enchanted Book**
3. Lock trade / reroll (phá lectern rồi đặt lại trước khi trade lần đầu) đến khi ra sách **Tăng Kinh Nghiệm**
4. Dùng **Anvil** + kiếm để ép sách vào

Lưu ý: enchant này khá hiếm (giống kiểu treasure như Mending), nên có thể phải reroll vài lần.

---

## Giải thích cấu trúc code (cho newbie)

```text
D:\Test
├── build.gradle              ← cấu hình build Forge
├── gradle.properties         ← mod_id, version, forge version
├── src/main/java/com/xpboost
│   ├── XpBoostMod.java                 ← class chính, đăng ký mod
│   ├── enchantment
│   │   ├── ModEnchantments.java        ← đăng ký enchant
│   │   └── ExperienceBoostEnchantment.java  ← logic enchant (cấp, chỉ kiếm, treasure...)
│   └── event
│       └── ModEvents.java              ← khi mob chết → nhân XP
└── src/main/resources
    ├── META-INF/mods.toml              ← thông tin mod cho Forge
    └── assets/xpboost/lang
        ├── en_us.json                  ← tên tiếng Anh
        └── vi_vn.json                  ← tên tiếng Việt
```

### File quan trọng

- `ExperienceBoostEnchantment.java`: quy tắc enchant (max 5 cấp, chỉ kiếm, chỉ trade)
- `ModEvents.java`: chỗ thực sự nhân XP khi giết mob
- `vi_vn.json`: đổi tên hiển thị trong game (nếu Minecraft đang để ngôn ngữ Việt)

---

## Lỗi thường gặp

**1. `Unsupported class file major version` / sai Java**  
→ Đang dùng Java cũ. Cài JDK 17 và trỏ IntelliJ/Gradle sang JDK 17.

**2. Build lâu / tải mãi**  
→ Bình thường lần đầu. Cần mạng ổn định.

**3. Vào game không thấy mod**  
→ Kiểm tra đúng folder `mods`, đúng phiên bản Forge 1.20.1, file là `xpboost-1.0.0.jar` (không copy nhầm file `-sources.jar`).

**4. Có sách nhưng không ép vào kiếm được**  
→ Enchant chỉ dành cho kiếm. Rìu/cuốc sẽ không nhận.

**5. Thủ thư mãi không bán**  
→ Reroll nghề thủ thư (tháo/đặt lại lectern trước khi player trade lần đầu với dân đó).

---

## Tuỳ chỉnh nhanh (sau này)

Muốn đổi hệ số XP → sửa hàm `getXpMultiplier` trong `ExperienceBoostEnchantment.java`.

Muốn đổi số cấp tối đa → sửa `getMaxLevel()`.

Muốn đổi tên tiếng Việt → sửa `vi_vn.json`.
