package oop;

public sealed class SealedClass permits FirstChild, SecondChild {

}

final class FirstChild extends SealedClass {}

sealed class SecondChild extends SealedClass permits LastOne {}

final class LastOne extends SecondChild {}