import { AdminModel } from './AdminModel';

export class WorkTimeModel{
    constructor(
        public workTimeId:Number,
        public fullDay:Number,
        public halfDay:Number,
        public adminId:AdminModel
    ){}
}