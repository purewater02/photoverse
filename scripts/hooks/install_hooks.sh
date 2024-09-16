#!/usr/bin/env bash

CURRENT_TOP_LEVEL_DIR_PATH="$(git rev-parse --show-toplevel)"

printf "Installing pre-commit to repo     "
if chmod ug+x "$CURRENT_TOP_LEVEL_DIR_PATH/scripts/hooks/pre-commit" &&
  ln -fs "$CURRENT_TOP_LEVEL_DIR_PATH/scripts/hooks/pre-commit" "$CURRENT_TOP_LEVEL_DIR_PATH/.git/hooks/pre-commit"; then
  printf ">> Success\n"
else
  printf ">> Failed\n"
  exit 1
fi
