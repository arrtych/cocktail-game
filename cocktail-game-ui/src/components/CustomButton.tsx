import { Button, ButtonProps as MuiButtonProps } from "@mui/material";
import React, { ReactNode } from "react";
import { JsxElement } from "typescript";
import { OverridableStringUnion } from "@mui/types";
import { IconButton } from "@mui/material";

interface ButtonProps {
  color?: MuiButtonProps["color"];
  sx?: MuiButtonProps["sx"];
  variant?: MuiButtonProps["variant"];
  children?: ReactNode;
  onClick?: () => void;
}

const CustomButton: React.FC<ButtonProps> = (props: ButtonProps) => {
  const { children, color, variant, onClick, sx } = {
    variant: "contained" as MuiButtonProps["variant"],
    ...props,
  };

  return (
    <>
      <Button
        variant={variant}
        sx={sx}
        color={color}
        size="medium"
        onClick={onClick}
      >
        {children}
      </Button>
    </>
  );
};

export default CustomButton;
