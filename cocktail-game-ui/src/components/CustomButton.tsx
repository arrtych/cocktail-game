import { Button, ButtonProps as MuiButtonProps } from "@mui/material";
import React, { ReactNode } from "react";
import { JsxElement } from "typescript";
import { OverridableStringUnion } from "@mui/types";
import { IconButton } from "@mui/material";

interface ButtonProps {
  color?: MuiButtonProps["color"];
  sx?: MuiButtonProps["sx"];
  variant?: MuiButtonProps["variant"];
  size?: MuiButtonProps["size"];
  disabled?: MuiButtonProps["disabled"];
  children?: ReactNode;
  onClick?: () => void;
}

const CustomButton: React.FC<ButtonProps> = (props: ButtonProps) => {
  const { children, color, variant, size, disabled, onClick, sx } = {
    variant: "contained" as MuiButtonProps["variant"],
    size: "small" as MuiButtonProps["size"],
    disabled: false as MuiButtonProps["disabled"],
    ...props,
  };

  return (
    <>
      <Button
        variant={variant}
        sx={sx}
        color={color}
        size={size}
        disabled={disabled}
        onClick={onClick}
      >
        {children}
      </Button>
    </>
  );
};

export default CustomButton;
