using System.IO.Compression;
using System.Text.RegularExpressions;
using Utils;

Build("VisualPassword.Chrome");
Build("VisualPassword.Firefox");
return;

void Build(string name)
{
    var sourceManifestPath = $"{AppContext.BaseDirectory}{name}{Path.DirectorySeparatorChar}manifest.json";
    var sourceHtmlPath = $"{AppContext.BaseDirectory}VisualPassword.Web{Path.DirectorySeparatorChar}index.html";
    var sourceIconPath = $"{AppContext.BaseDirectory}VisualPassword.Web{Path.DirectorySeparatorChar}ico.png";

    var targetBuildPath = $"{AppContext.BaseDirectory}Build{Path.DirectorySeparatorChar}";
    var targetExtPath = $"{targetBuildPath}{name}.Extension{Path.DirectorySeparatorChar}";
    var targetHtmlPath = $"{targetExtPath}index.html";
    var targetIconPath = $"{targetExtPath}ico48.png";
    var targetJsPath = $"{targetExtPath}script.js";
    var targetManifestPath = $"{targetExtPath}manifest.json";
    var targetZipPath = $"{targetBuildPath}{name}.Extension.zip";

    if (Directory.Exists(targetExtPath)) Directory.Delete(targetExtPath, true);
    Directory.CreateDirectory(targetExtPath);

    var html = File.ReadAllText(sourceHtmlPath);
    var js = "";
    var manifest = File.Exists(sourceManifestPath) ? File.ReadAllText(sourceManifestPath) : "";
    var icon = File.Exists(sourceIconPath) ? File.ReadAllBytes(sourceIconPath) : null;

    var match = ScriptRegex().Match(html);
    if (match.Success) js = match.Groups[1].Value;

    html = ScriptRegex().Replace(html, """<script src="script.js"></script>""");
    html = html.Replace("<body>", """<body class="chromeExtension">""");

    File.WriteAllText(targetHtmlPath, html);
    File.WriteAllText(targetJsPath, js);
    File.WriteAllText(targetManifestPath, manifest);
    if (icon != null) File.WriteAllBytes(targetIconPath, icon);

    var zipFolder = new ZipFolder(targetZipPath);
    zipFolder.WriteAllText("index.html", html, CompressionLevel.SmallestSize);
    zipFolder.WriteAllText("script.js", js, CompressionLevel.SmallestSize);
    zipFolder.WriteAllText("manifest.json", manifest, CompressionLevel.SmallestSize);
    if (icon != null) zipFolder.WriteAllBytes("ico48.png", icon, CompressionLevel.SmallestSize);
}

internal abstract partial class Program
{
    [GeneratedRegex(@"(?s)<script\b[^>]*>(.*?)</script>")]
    private static partial Regex ScriptRegex();
}