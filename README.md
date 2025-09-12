[<a href="docs/README-RU.md">Русский</a>]

<a href='https://ko-fi.com/alex198' target='_blank'><img height='36' style='border:0px;height:36px;' src='https://storage.ko-fi.com/cdn/kofi6.png?v=6' border='0' alt='Buy Me a Coffee at ko-fi.com'/></a>

# Visual Password

Visual Password is a program that helps you easily recover secure passwords without writing them down or storing them in any service. You simply remember a couple of emojis and one keyword — like a secret code. When you need a password, the program combines those remembered emojis and the keyword to instantly generate a long, complex password directly on your device.

Everything happens locally: no servers, no internet, no databases — the program never sends or saves your passwords.

In other words: you only keep small memory cues in your head, and the program turns them into a truly secure password that cannot be stolen.

![screen0.png](docs/imgs/screen0.png)

To create passwords, Visual Password uses a multi-layered cryptographic password-generation algorithm that takes your input data (keyword, PIN, visual password). It applies multi-stage AES-GCM encryption, bit mixing for maximum entropy, and multi-stage SHA-256 hashing for cryptographic strength. PBKDF2 key derivation protects against rainbow-table attacks and adds computational complexity beyond the reach of modern supercomputers. Multiple cascade-encryption layers enhance overall resilience and provide cryptographic redundancy. The final step uses a high-entropy pseudorandom generator based on cryptographically secure data, ensuring the uniqueness and complexity of the password even with very similar inputs.

# Usage Recommendations

- Keyword field: Enter something that characterizes your password’s purpose, e.g., `google`, `facebook`, etc.

- PIN field: Enter any string (e.g., `123a`). The PIN increases entropy and reduces collision probability with other users.

- Character set & length: Choose your desired character set and password length.

- Visual Password field: Enter a sequence of emojis. To help memorization, use not only their pictures but also the alphanumeric labels on each emoji button and the color variations when pressing an emoji.

The same combination of input data will always produce the same password.

1. Copy the generated password.
2. Close Visual Password.
3. To retrieve the password later, simply re-enter the same inputs.

# Installation

Latest release [https://github.com/viruseg/VisualPassword/releases](https://github.com/viruseg/VisualPassword/releases)

### HTML-file
Download VisualPassword.Web.html and run it locally or place it on any hosting. Limitation: It will not work over the http protocol.

### Android
Download VisualPassword.Android.apk and install it on your phone.

### Google Chrome, Edge
Download VisualPassword.Chrome.Extension.zip and unzip to any folder.
Open the chrome://extensions page.
Turn on developer mode in the upper-right corner.
Click Download the unpacked extension.
Find and select the folder with the unpacked extension.

### Firefox
Install via [Firefox Browser Add-ons](https://addons.mozilla.org/firefox/addon/visual-password/).

### Telegram
[@VisualPassword_bot](https://t.me/VisualPassword_bot)

### Web

[https://visualpassword.pages.dev/](https://visualpassword.pages.dev/)

[https://viruseg.github.io/VisualPassword/](https://viruseg.github.io/VisualPassword/)

# License

Attribution-NonCommercial 4.0 International

# Third-party libraries

- [MonoIcons](https://icons.mono.company/)