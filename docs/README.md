# CS 131 LE 3 Numerical Methods Implementation

**Package Name:** `edu.up.numeds`

**Author:** Vincent Fiestada <vffiestada@gmail.com>

This package consists of a Java class library with classes for LU-decomposable matrices and Lagrange Interpolators, as well as a simply JavaFX application that consumes the class library. These components are described below. 

The class library is designed to be separate from the GUI application and as generic as possible. For more information, please see the in-code documentation for the various classes.

## Matrix 

A basic representation of a square matrix with methods to access and change any element within the matrix.

## DecomposableMatrix 

**Extends `Matrix`**

An extension of Matrix that can be decomposed into LU using Crout's Method. Decomposition must be manually triggered. The L and U matrices are maintained internally.

## Interpolator 

*(Abstract)*

An abstract 2D Interpolator class which exposes various APIs useful for interpolation. It maintains an internal list of known points used for interpolation. The concrete implementation is left out, for example, the LagrangeInterpolator class implements this class using Lagrange Polynomial interpolation.

## LagrangeInterpolator

**Extends `Interpolator`**

A concrete implementation of `Interpolator`, which uses Lagrange polynomial interpolation. This class uses Doubles but can be easily modified to use other subclasses of Number such as BigInteger, BigDecimal, etc.

## NumericalMethods

*[Main Class]*

This is the main class of the package, and the entry point for the JavaFX GUI application. It consumes the aforementioned class library and provides a graphical interface for relatively easy use of the numerical methods.

## How to Run

First, make sure you have the following prerequisites installed on your machine and added to your PATH variable:

- Java Runtime Environment 1.8.0_112-b15
- Gradle 3.1

The project is written in Java and requires the Java runtime to be used. Gradle is the build system used.

### Compiling from Source

To compile from the source code using Gradle, run the following command in the project directory.

```
gradle build
```

### Running Jar file 

Gradle should create a jar file inside `build\libs\numerical_methods.jar`. Execute this to use the application:

```
java -jar build\libs\numerical_methods.jar
```