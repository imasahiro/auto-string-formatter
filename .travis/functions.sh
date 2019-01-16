function install_jdk() {
  if [[ ! -x "$JDK_HOME/bin/java" ]]; then
    mkdir -p "$HOME/.jdk/$JDK_VERSION_MAJOR"
    wget --no-cookies --no-check-certificate \
         --header='Cookie: oraclelicense=accept-securebackup-cookie' \
         --output-document="$JDK_TARBALL" \
         "$JDK_DOWNLOAD_URL"

    rm -vfr "$JDK_HOME"
    mkdir "$JDK_HOME"
    tar zxvf "$JDK_TARBALL" --strip 1 -C "$JDK_HOME"
    rm -vf "$JDK_TARBALL"
    # Remove the old versions
    find $HOME/.jdk/$JDK_VERSION_MAJOR -mindepth 1 -maxdepth 1 -type d -not -name jdk-${JDK_VERSION} | xargs rm -rf
  fi
}

function install_symlink() {
  local DEFAULT_JDK_HOME="$HOME/.jdk/default_$1"
  if [[ "$(readlink "$DEFAULT_JDK_HOME")" != "$JDK_HOME" ]]; then
    rm -fr "$DEFAULT_JDK_HOME"
    ln -sv "$JDK_HOME" "$DEFAULT_JDK_HOME"
  fi
  "$DEFAULT_JDK_HOME/bin/java" -version
}
