import { UserModel } from './UserModel';

export class AdminModel{
    constructor(
        public adminId:Number,
        public name:String,
        public userId:UserModel
    ){}
}