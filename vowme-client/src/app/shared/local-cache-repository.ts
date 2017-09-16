export class LocalCacheRepository<T> {

	private localCache:T[] = [];
	defaultBatchSize:number = 12;
	private capicity:number = 50;

	haveEnoughData:boolean = true;

	set batchSize(dSize: number) {
		this.defaultBatchSize = dSize;
	}

	cacheData(data: T[]) {
		this.localCache = this.localCache.concat(data);
		this.normalizeHaveEnoughDataAttribute();
	}

	clear(){
		this.localCache = [];
	}

	private normalizeHaveEnoughDataAttribute() {
		let isEnoughData = false;
		if( this.localCache != null && (this.localCache.length >= this.defaultBatchSize) ) {
			isEnoughData = true;
		}

		this.haveEnoughData = isEnoughData;
	}

	next(): T[] {

		let arrayOfDataToReturn = [];
		let isEnoughData = false;

		if( this.haveEnoughData ) {

			arrayOfDataToReturn = this.localCache.slice(0, this.defaultBatchSize);;

			let nextlocalCacheArrayLength = this.localCache.length - arrayOfDataToReturn.length;

			if( nextlocalCacheArrayLength <= 0 ) {
				this.localCache = [];
				isEnoughData = false;
			} else if( nextlocalCacheArrayLength >= this.defaultBatchSize ) {
				isEnoughData = true;
				this.localCache = this.localCache.slice(this.defaultBatchSize, this.localCache.length);
			} else {
				isEnoughData = false;
				this.localCache = this.localCache.slice(this.defaultBatchSize, this.localCache.length);
			}

			this.haveEnoughData = isEnoughData;

			return arrayOfDataToReturn;
		}
		
		arrayOfDataToReturn = this.localCache;
		this.localCache = [];

		this.haveEnoughData = isEnoughData;

		return arrayOfDataToReturn;
	}

}