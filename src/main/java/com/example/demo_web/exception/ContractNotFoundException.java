package com.example.demo_web.exception;

public class ContractNotFoundException extends RuntimeException
{
    public ContractNotFoundException( String message )
    {
        super( message );
    }
}
