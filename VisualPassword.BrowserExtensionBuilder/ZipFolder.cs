using System.IO.Compression;

namespace Utils;

public sealed class ZipFolder
{
    private readonly string zipPath;

    public ZipFolder(string zipPath)
    {
        this.zipPath = zipPath;

        if (!File.Exists(zipPath)) CreateEmptyArchive();
    }

    private void CreateEmptyArchive()
    {
        using var archive = ZipFile.Open(zipPath, ZipArchiveMode.Create);
    }

    public string ReadAllText(string entryName)
    {
        var bytes = ReadAllBytes(entryName);
        return System.Text.Encoding.UTF8.GetString(bytes);
    }

    public byte[] ReadAllBytes(string entryName)
    {
        using var archive = ZipFile.Open(zipPath, ZipArchiveMode.Read);
        var entry = archive.GetEntry(entryName);

        if (entry == null) throw new FileNotFoundException($"The file '{entryName}' was not found in the archive.");

        using var memoryStream = new MemoryStream();
        using (var entryStream = entry.Open())
        {
            entryStream.CopyTo(memoryStream);
        }

        return memoryStream.ToArray();
    }

    public void WriteAllText(string entryName, string content, CompressionLevel compressionLevel = CompressionLevel.NoCompression)
    {
        var bytes = System.Text.Encoding.UTF8.GetBytes(content);
        WriteAllBytes(entryName, bytes, compressionLevel);
    }

    public void WriteAllBytes(string entryName, byte[] content, CompressionLevel compressionLevel = CompressionLevel.NoCompression)
    {
        using var archive = ZipFile.Open(zipPath, ZipArchiveMode.Update);

        var existingEntry = archive.GetEntry(entryName);
        existingEntry?.Delete();

        var newEntry = archive.CreateEntry(entryName, compressionLevel);

        using var entryStream = newEntry.Open();
        entryStream.Write(content, 0, content.Length);
    }

    public bool FileExists(string entryName)
    {
        using var archive = ZipFile.OpenRead(zipPath);
        return archive.GetEntry(entryName) != null;
    }

    public string[] GetEntries(string? path)
    {
        if (path == null) path = "/";
        path = path.Replace('\\', '/');
        if (!path.EndsWith('/')) path += '/';
        path = path.TrimStart('/');

        var countSlashes = path.Count(c => c == '/');

        using var archive = ZipFile.OpenRead(zipPath);

        return archive.Entries
                      .Select(p => p.FullName)
                      .Where(p => p.StartsWith(path, StringComparison.OrdinalIgnoreCase) &&
                                  (p.EndsWith('/') && p.Count(c => c == '/') == countSlashes + 1 ||
                                   p != path && p.Count(c => c == '/') == countSlashes))
                      .ToArray();
    }
}