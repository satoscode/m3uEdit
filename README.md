# m3uEdit

## Overview

`m3uEdit` is a small Java command‑line utility for working with IPTV `.m3u` playlists.
It helps you:

- **Clean and reformat** raw M3U playlists.
- **List, filter and delete categories** (`group-title`) from a playlist.
- **Generate folders and `.strm` files** from an M3U, organized by category.
- **Convert a single M3U file into a structured `TV` / `Movies` library** suitable for media servers (Kodi, Jellyfin, Plex, etc.).

The project is intentionally simple and menu‑driven so that it can be used directly
from a terminal by non‑developers.

## Features

- **Interactive menu** via `Main`:
  - `1` – Format list (normalize and clean the base M3U file).
  - `2` – Create list by category (output M3U with only one category).
  - `3` – Show list of categories.
  - `4` – Delete a category from the base file.
  - `5` – Create folders and `.strm` files per category.
  - `6` – Show TV categories from the original (unformatted) M3U.
  - `7` – Delete a TV category from the original M3U.
  - `8` – Create list by TV category from the original M3U.
  - `9` – Process an M3U file into a `TV` / `Movies` folder structure using `M3UProcessor`.
  - `0` – Exit.

- **Playlist processing helpers** (`Tools`):
  - Uses regular expressions to parse `#EXTINF` lines.
  - Cleans problematic characters from titles and categories so they can be used
    safely as folder / file names.
  - Can generate `.strm` files where each file points to the original stream URL.

- **Advanced library builder** (`M3UProcessor`):
  - Reads a given M3U file and detects:
    - **Movies** (URLs containing `/movie/`).
    - **Series** (titles with patterns like `S01 E02` and URLs containing `/series/`).
  - Creates a directory tree such as:
    - `Movies/<Group>/<Movie Title>/*.strm`
    - `TV/<Group>/<Series Name>/Season <N>/*.strm`

## Project structure

- `src/Main.java` – Entry point; shows the interactive menu and calls the other utilities.
- `src/Tools.java` – Core tools that operate on the configured playlist files.
- `src/configuration/Configuration.java` – Central configuration:
  - `FILE_PATH` – input playlist path (main M3U).
  - `FILE_PATH_OUTPUT` – output playlist path (filtered / formatted M3U).
  - Regular expressions for recognizing supported `#EXTINF` formats.
- `src/mypackage/M3UProcessor.java` – Stand‑alone M3U processor for building
  a complete `TV` / `Movies` library from one playlist.
- `src/resources/` – Default location for sample or user‑provided M3U files and
  for generated folders / `.strm` files (depending on the operation).

## Requirements

- Java 11+ (should also work on newer versions).
- A terminal or command prompt.
- At least one valid `.m3u` playlist file that follows a typical IPTV format,
  for example lines like:

```text
#EXTINF:-1 tvg-id="" tvg-name="Sample Movie" tvg-logo="" group-title="Movies EN",Sample Movie
http://example.com/movie/sample_movie.mkv
```

## Setup

1. **Clone or copy the project** into a local directory.
2. Make sure you have Java installed:

   ```bash
   java -version
   ```

3. From the project root, compile all sources:

   ```bash
   javac -d out $(find src -name '*.java')
   ```

If compilation succeeds, the compiled `.class` files will be under the `out` directory.

## Providing an input M3U

By default, `Tools` uses the paths defined in
`src/configuration/Configuration.java`:

- `FILE_PATH` – input playlist (default: `src/resources/completo-list.m3u`).
- `FILE_PATH_OUTPUT` – output playlist (default: `src/resources/movieList.m3u`).

To use your own playlist:

1. Place your `.m3u` file at `src/resources/completo-list.m3u` **or**
2. Edit `Configuration.FILE_PATH` and `Configuration.FILE_PATH_OUTPUT`
   to point to your own paths, then recompile.

The `M3UProcessor` (menu option `9`) is more flexible: when you choose this
option, the program will ask for the **full path** of the M3U file to process,
and will use that path directly.

## Running the program

From the project root:

```bash
java -cp out Main
```

You will see an interactive menu:

```text
Please choose an option:
1. Format list
2. Create list by category
3. Show list of categories
4. Delete category
5. Create folders
6. Show list of TV categories
7. Delete TV category
8. Create list by category 2
9. Process M3U file
0. Exit
```

Type the corresponding number and press Enter.

### Typical workflows

- **Clean and inspect categories from a playlist**
  1. Ensure `FILE_PATH` points to your source M3U.
  2. Run the program and select `1` (Format list).
  3. Select `3` (Show list of categories) to see the cleaned, unique, sorted
     list of categories.

- **Generate a new M3U with only one category**
  1. After formatting the list (option `1`), run the program again.
  2. Select `2` (Create list by category).
  3. When prompted, type the category name (must match exactly).
  4. The result will be written to `FILE_PATH_OUTPUT`.

- **Create folders and `.strm` files by category**
  1. Make sure the formatted M3U exists (run option `1` first).
  2. Select `5` (Create folders).
  3. The program will:
     - Create one folder per category under `src/resources/`.
     - Create a `.strm` file per entry with its stream URL inside.

- **Build a full TV / Movies structure from a playlist**
  1. Run the program and select `9` (Process M3U file).
  2. Enter the full path to your `.m3u` file when asked.
  3. The program will:
     - Create a `Movies` directory tree for movie entries (`/movie/` in the URL).
     - Create a `TV` directory tree for series entries (`/series/` in the URL and titles like `S01 E01`).
     - Generate `.strm` files for each movie / episode.

## Purpose of the project

The main goal of `m3uEdit` is to make IPTV playlists easier to manage and to
bridge the gap between **raw `.m3u` files** and **well‑structured media
libraries**. Instead of manually editing a huge playlist, you can:

- Normalize and clean its contents.
- Quickly inspect and manage categories.
- Export subsets of the playlist per category.
- Automatically generate `.strm` files and folders that many media servers
  understand out of the box.

This makes it easier for any user to go from an IPTV M3U file to a usable,
organized media library with minimal manual work.