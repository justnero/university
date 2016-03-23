using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Lab._03
{
    class InvalidValueException: Exception
    {

        new public string Message { get; set; }

        public InvalidValueException(FieldType fieldType)
        {
            switch (fieldType)
            {
                case FieldType.Name:
                    Message = "Name field can`t be changed";
                    break;
                case FieldType.Address:
                    Message = "Address field can`t be empty";
                    break;
                case FieldType.Index:
                    Message = "Index field is invalid";
                    break;
                default:
                    Message = "Unknown field is invalid";
                    break;
            }
        }

    }

    enum FieldType
    {
        Name,
        Address,
        Index
    }
}
